package com.sky.framework.task;

import com.alibaba.fastjson.JSONObject;
import com.sky.framework.task.entity.PopTask;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.entity.builder.PopTaskBuilder;
import com.sky.framework.task.entity.builder.TaskPOBuilder;
import com.sky.framework.task.enums.PopTaskResult;
import com.sky.framework.task.util.DateUtil;
import com.sky.framework.task.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by easyfun on 2018/3/27.
 * 任务生产过程
 * 1. 插入任务检查mq
 * 2. 任务检查，重复性检查
 * 3. 插入redis
 * 3. 插入任务mq
 */
@Service
public class TaskManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManager.class);

    private static final int PENDING_TIME_OUT_MINUTES = 30;

    @Autowired
    private StringRedisTemplate taskRedisTemplate;

    @Autowired
    private TaskDAOService taskDAOService;

    /**
     * 安全
     * 1. 插入任务检查mq
     * @param task
     * @return
     */
    public boolean pushTask(Task task) {
        TaskPO taskPO = TaskPOBuilder.buildTaskPO(task);

        if (!validateTaskPO(taskPO)) {
            return false;
        }

        // 1.插入t_task:pending:zset
        if (!insertTaskPending(taskPO)) {
            LOGGER.error("任务插入redis:t_task:pending:zset失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 任务插入redis失败，增加监控
            return false;
        }

        // 2.插入t_task:info:hash
        if (!insertTaskInfo(taskPO)) {
            LOGGER.error("任务插入redis:t_task:info:hash失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 任务插入redis失败，增加监控
            return false;
        }

        // 3.插入t_task:executing:zset
        if (!insertTaskExecuting(taskPO)) {
            LOGGER.error("任务插入redis:t_task:executing:zset失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 任务插入redis失败，增加监控
            return false;
        }
        return true;
    }

    public PopTask popPendingTask(String handler, Date date) {
        String key = TaskRedisKey.buildKeyPending(handler);

        //必须在消费处加锁
        Set<ZSetOperations.TypedTuple<String>> taskKeys = taskRedisTemplate.opsForZSet().rangeByScoreWithScores(key, 0, date.getTime(), 0, 1);
        if (CollectionUtils.isEmpty(taskKeys)) {
            return PopTaskBuilder.build(null, PopTaskResult.FAIL_SLEEP);
        }
        if (taskKeys.size() > 1) {
            LOGGER.error("获取pending错误, size={}", taskKeys.size());
            return PopTaskBuilder.build(null, PopTaskResult.FAIL_SLEEP);
        }

        String taskKey = null;
        for (ZSetOperations.TypedTuple<String> task : taskKeys) {
            taskKey = task.getValue();
        }
        if (StringUtils.isBlank(taskKey)) {
            LOGGER.error("找不到任务信息t_task:pending:zset，taskKey={}", taskKey);
            taskRedisTemplate.opsForZSet().remove(key, taskKey);
            return PopTaskBuilder.build(null, PopTaskResult.TRY_NEXT);
        }

        //改t_task:pending:zset,当前时间+30分

        String taskInfo = (String) taskRedisTemplate.opsForHash().get(TaskRedisKey.buildKeyInfo(handler), taskKey);
        if (StringUtils.isBlank(taskInfo)) {
            LOGGER.error("找不到任务信息t_task:info:hash，taskKey={}", taskKey);
            taskRedisTemplate.opsForZSet().remove(key, taskKey);
            return PopTaskBuilder.build(null, PopTaskResult.TRY_NEXT);
        }
        return PopTaskBuilder.build(JSONObject.parseObject(taskInfo, TaskPO.class), PopTaskResult.SUCCESS);
    }

    public PopTask popExecutingTask(String handler, Date date) {
        String key = TaskRedisKey.buildKeyExecuting(handler);
        //TODO:后续加锁
        Set<ZSetOperations.TypedTuple<String>> taskKeys = taskRedisTemplate.opsForZSet().rangeByScoreWithScores(key, 0, date.getTime(), 0, 1);
        if (CollectionUtils.isEmpty(taskKeys)) {
            LOGGER.debug("获取executing，size=0");
            return PopTaskBuilder.build(null, PopTaskResult.FAIL_SLEEP);
        }
        if (taskKeys.size() > 1) {
            LOGGER.error("获取executing错误, size={}", taskKeys.size());
            return PopTaskBuilder.build(null, PopTaskResult.FAIL_SLEEP);
        }

        String taskKey =  null;
        for (ZSetOperations.TypedTuple<String> task : taskKeys) {
            taskKey = task.getValue();
        }
        if (0 == taskRedisTemplate.opsForZSet().remove(key, taskKey)) {
            LOGGER.debug("获取executing失败, 移除失败, taskKey={}", taskKey);
            return PopTaskBuilder.build(null, PopTaskResult.TRY_NEXT);
        }

        String taskInfo = (String) taskRedisTemplate.opsForHash().get(TaskRedisKey.buildKeyInfo(handler), taskKey);
        if (StringUtils.isBlank(taskInfo)) {
            LOGGER.debug("找不到任务信息t_task:info:hash, taskKey={}", taskKey);
            taskRedisTemplate.opsForZSet().remove(key, taskKey);
            return PopTaskBuilder.build(null, PopTaskResult.TRY_NEXT);
        }
        TaskPO taskPO = JSONObject.parseObject(taskInfo, TaskPO.class);

        Date nextTime = taskPO.getNextTime()==null ? new Date() : taskPO.getNextTime();
        nextTime = DateUtil.addMiliSeconds(nextTime, taskPO.getRetryInterval());
        taskRedisTemplate.opsForZSet().add(TaskRedisKey.buildKeyPending(handler), taskKey, DateUtil.addMinutes(nextTime, PENDING_TIME_OUT_MINUTES).getTime());
        return PopTaskBuilder.build(taskPO, PopTaskResult.SUCCESS);
    }

    public void deleteTask(String handler, String taskKey) {
        taskRedisTemplate.opsForZSet().remove(TaskRedisKey.buildKeyExecuting(handler), taskKey);
        taskRedisTemplate.opsForHash().delete(TaskRedisKey.buildKeyInfo(handler), taskKey);
        taskRedisTemplate.opsForZSet().remove(TaskRedisKey.buildKeyExecuting(handler), taskKey);
    }

    public boolean successTask(TaskPO taskPO) {
        return safeUpdateRedis(taskPO);
    }

    public boolean failTask(TaskPO taskPO) {
        return safeUpdateRedis(taskPO);
    }

    public boolean retryTask(TaskPO taskPO) {
        return safeUpdateRedisForRetryTask(taskPO);
    }

    public boolean timeoutSupplyRetryTask(TaskPO taskPO) {
        return safeUpdateRedisForRetryTask(taskPO);
    }

    public void finishTask(TaskPO taskPO) {
        //1.插入mysql
        taskDAOService.insertTaskToDB(taskPO);
        deleteTask(taskPO.getHandler(), taskPO.getTaskKey());
    }


    private boolean validateTaskPO(TaskPO taskPO) {
        if (StringUtils.isBlank(taskPO.getTaskKey()) || StringUtils.isBlank(taskPO.getHandler())) {
            return  false;
        }
        return true;
    }

    private boolean insertTaskPending(TaskPO taskPO) {
        try {
            Date nextTime = DateUtil.addMinutes(taskPO.getNextTime(), PENDING_TIME_OUT_MINUTES);
            taskRedisTemplate.opsForZSet().add(TaskRedisKey.buildKeyPending(taskPO), taskPO.getTaskKey(), nextTime.getTime());
        } catch (Exception e) {
            LOGGER.error("生产任务错误.插入t_task:pending:zset失败.", e);
            return false;
        }
        return true;
    }

    private boolean insertTaskInfo(TaskPO taskPO) {
        try {
            taskRedisTemplate.opsForHash().put(TaskRedisKey.buildKeyInfo(taskPO), taskPO.getTaskKey(), JsonUtil.toJSONString(taskPO));
        } catch (Exception e) {
            LOGGER.error("生产任务错误,插入t_task:info:hash失败.", e);
            return false;
        }
        return true;
    }

    /**
     * 任务完成(成功，失败)，入t_task:pending:zset
     * @param taskPO
     * @return
     */
    private boolean insertTaskPendingForFinishedTask(TaskPO taskPO) {
        try {
            taskRedisTemplate.opsForZSet().add(TaskRedisKey.buildKeyPending(taskPO), taskPO.getTaskKey(), new Date().getTime());
        } catch (Exception e) {
            LOGGER.error("生产任务错误.插入t_task:pending:zset失败.", e);
            return false;
        }
        return true;
    }


    private boolean insertTaskExecuting(TaskPO taskPO) {
        try {
            taskRedisTemplate.opsForZSet().add(TaskRedisKey.buildKeyExecuting(taskPO), taskPO.getTaskKey(), taskPO.getNextTime().getTime());
        } catch (Exception e) {
            LOGGER.error("生产任务错误.插入t_task:executing:zset失败.", e);
            return false;
        }
        return true;
    }

    private boolean safeUpdateRedis(TaskPO taskPO) {
        //1.更新t_task:info:hash
        if (!insertTaskInfo(taskPO)) {
            LOGGER.error("更新任务失败t_task:info:hash, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 更新失败，增加监控
            return false;
        }

        // 2.插入t_task:pending:zset
        if (!insertTaskPendingForFinishedTask(taskPO)) {
            LOGGER.error("任务插入t_task:pending:zset失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 更新失败，增加监控
            return false;
        }
        return true;
    }

    private boolean safeUpdateRedisForRetryTask(TaskPO taskPO) {
        // 1.插入t_task:pending:zset
        if (!insertTaskPending(taskPO)) {
            LOGGER.error("任务插入t_task:pending:zset失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 更新失败，增加监控
            return false;
        }

        // 2.插入t_task:info:hash
        if (!insertTaskInfo(taskPO)) {
            LOGGER.error("任务插入redis:t_task:info:hash失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 任务插入redis失败，增加监控
            return false;
        }

        // 3.入t_task:executing:zset
        if (!insertTaskExecuting(taskPO)) {
            LOGGER.error("任务插入t_task:executing:zset失败, taskPO={}", JsonUtil.toJSONString(taskPO));
            // TODO: 更新失败，增加监控
            return false;
        }

        return true;
    }

}
