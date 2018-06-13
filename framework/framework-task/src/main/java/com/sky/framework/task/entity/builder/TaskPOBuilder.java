package com.sky.framework.task.entity.builder;

import com.sky.framework.task.Task;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.enums.RetryStrategy;
import com.sky.framework.task.enums.TaskMode;
import com.sky.framework.task.enums.TaskStatus;

import java.util.*;

/**
 * Created by easyfun on 2018/3/27.
 */
public class TaskPOBuilder {
/*    public static Map<String, String> buildTaskPORedisMap(TaskPO po) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", RedisUtil.buildRedisString(po.getKey()));
        map.put("param", RedisUtil.buildRedisString(po.getParam()));
        map.put("handler", RedisUtil.buildRedisString(po.getHandler()));
        map.put("retryStrategy", po.getRetryStrategy().name());
        map.put("retryInterval", String.valueOf(po.getRetryInterval()));
        map.put("maxRetryTimes", String.valueOf(po.getMaxRetryTimes()));
        map.put("taskStatus", po.getTaskStatus().name());
        map.put("retriedTimes", String.valueOf(po.getRetriedTimes()));
        map.put("createdTime", DateUtil.formatDate(po.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("firstTime", DateUtil.formatDate(po.getFirstTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("lastTime", DateUtil.formatDate(po.getLastTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("nextTime", DateUtil.formatDate(po.getNextTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("doneTime", DateUtil.formatDate(po.getDoneTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("updatedTime", DateUtil.formatDate(po.getUpdatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));

        map.put("currentPausePoint", RedisUtil.buildRedisString(po.getCurrentPausePoint()));
        return map;
    }*/

/*    public static Map<String, String> buildDoneTaskPORedisMap(TaskPO po) {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("key", RedisUtil.buildRedisString(po.getKey()));
//        map.put("param", RedisUtil.buildRedisString(po.getParam()));
//        map.put("handler", RedisUtil.buildRedisString(po.getHandler()));
//        map.put("retryStrategy", po.getRetryStrategy().name());
//        map.put("retryInterval", String.valueOf(po.getRetryInterval()));
//        map.put("maxRetryTimes", String.valueOf(po.getMaxRetryTimes()));
        map.put("taskStatus", po.getTaskStatus().name());
        map.put("retriedTimes", String.valueOf(po.getRetriedTimes()));
//        map.put("createdTime", DateUtil.formatDate(po.getCreatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("firstTime", DateUtil.formatDate(po.getFirstTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("lastTime", DateUtil.formatDate(po.getLastTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        map.put("nextTime", DateUtil.formatDate(po.getNextTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("doneTime", DateUtil.formatDate(po.getDoneTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("updatedTime", DateUtil.formatDate(po.getUpdatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));

//        map.put("currentPausePoint", RedisUtil.buildRedisString(po.getCurrentPausePoint()));
        return map;
    }*/

/*    public static Map<String, String> buildRetryTaskPORedisMap(TaskPO po) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("taskStatus", po.getTaskStatus().name());
        map.put("retriedTimes", String.valueOf(po.getRetriedTimes()));
//        map.put("firstTime", DateUtil.formatDate(po.getFirstTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("lastTime", DateUtil.formatDate(po.getLastTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("nextTime", DateUtil.formatDate(po.getNextTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        map.put("updatedTime", DateUtil.formatDate(po.getUpdatedTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));

        return map;
    }*/

    public static TaskPO buildTaskPO(Task task) {
        TaskPO po = new TaskPO();
        po.setTaskKey(task.getTaskKey());
        po.setParam(task.getParam());
        po.setHandler(task.getHandler());
        po.setBusiness(task.getBusiness());
        po.setTaskMode(task.getTaskMode().getValue());
        po.setRetryStrategy(task.getRetryStrategy().getValue());
        po.setRetryInterval(task.getRetryInterval());
        po.setMaxRetryTimes(task.getMaxRetryTimes());
        po.setTaskStatus(TaskStatus.ACCEPTED.getValue());
        po.setRetriedTimes(0);
        Date current = new Date();
        po.setCreatedTime(current);
        po.setFirstTime(null);
        po.setLastTime(null);
        po.setNextTime(current);
        po.setDoneTime(null);
        po.setUpdatedTime(current);
        return po;
    }

    public static TaskPO buildTestTaskPO() {
        TaskPO po = new TaskPO();
        po.setTaskKey(String.valueOf(System.nanoTime()));
        po.setParam("");
        po.setHandler("test99");
        po.setTaskMode(TaskMode.NORMAL.getValue());
        po.setRetryStrategy(RetryStrategy.NORMAL.getValue());
        po.setRetryInterval(60*1000);
        po.setMaxRetryTimes(10);
        po.setTaskStatus(TaskStatus.RETRYING.getValue());
        po.setRetriedTimes(0);
        Date current = new Date();
        po.setCreatedTime(current);
        po.setFirstTime(null);
        po.setLastTime(null);
        po.setNextTime(current);
        po.setDoneTime(null);
        po.setUpdatedTime(current);

        return po;
    }

    /**
     * TODO:必须保证不可变，这个实现可能不满足
     * @param taskPO
     * @return
     */
    public static TaskPO copy(TaskPO taskPO) {
        TaskPO po = new TaskPO();
        po.setTaskKey(taskPO.getTaskKey());
        po.setParam(taskPO.getParam());
        po.setHandler(taskPO.getHandler());
        po.setBusiness(taskPO.getBusiness());
        po.setTaskMode(taskPO.getTaskMode());
        po.setRetryStrategy(taskPO.getRetryStrategy());
        po.setRetryInterval(taskPO.getRetryInterval());
        po.setMaxRetryTimes(taskPO.getMaxRetryTimes());
        po.setTaskStatus(taskPO.getTaskStatus());
        po.setProgress(taskPO.getProgress());
        po.setRetriedTimes(taskPO.getRetriedTimes());
        po.setCreatedTime(taskPO.getCreatedTime());
        po.setFirstTime(taskPO.getFirstTime());
        po.setLastTime(taskPO.getLastTime());
        po.setNextTime(taskPO.getNextTime());
        po.setDoneTime(taskPO.getDoneTime());
        po.setUpdatedTime(taskPO.getUpdatedTime());
        return po;
    }

//    public static TaskPO buildTaskPO(Map<Object, Object> map) {
//        TaskPO po = new TaskPO();
//        po.setTaskKey((String)map.get("taskKey"));
//        po.setParam((String)map.get("param"));
//        po.setHandler((String)map.get("handler"));
//        po.setRetryStrategy(Integer.parseInt((String )map.get("retryStrategy")));
//        po.setRetryInterval(Integer.parseInt((String)map.get("retryInterval")));
//        po.setMaxRetryTimes(Integer.parseInt((String)map.get("maxRetryTimes")));
//        po.setTaskStatus(Integer.parseInt((String)map.get("taskStatus")));
//        po.setRetriedTimes(Integer.parseInt((String)map.get("retriedTimes")));
//        po.setCreatedTime(DateUtil.parseString((String)map.get("createdTime"), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        po.setFirstTime(DateUtil.parseString((String)map.get("firstTime"), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        po.setLastTime(DateUtil.parseString((String)map.get("lastTime"), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        po.setNextTime(DateUtil.parseString((String)map.get("nextTime"), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        po.setDoneTime(DateUtil.parseString((String)map.get("doneTime"), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        po.setUpdatedTime(DateUtil.parseString((String)map.get("updatedTime"), DateUtil.YYYY_MM_DD_HH_MM_SS));
//        return po;
//    }

    public static void updateLastTime(TaskPO taskPO, Date current) {
        taskPO.setLastTime(current);
        taskPO.setUpdatedTime(current);
    }

    public static void updateDoneTime(TaskPO taskPO, Date current) {
        taskPO.setLastTime(current);
        taskPO.setDoneTime(current);
        taskPO.setUpdatedTime(current);
    }

}
