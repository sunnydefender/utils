package com.sky.framework.task.runnable;

import com.alibaba.fastjson.JSON;
import com.sky.framework.task.entity.PopTask;
import com.sky.framework.task.enums.PopTaskResult;
import com.sky.framework.task.util.DateUtil;
import com.sky.framework.task.TaskManager;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.entity.builder.TaskPOBuilder;
import com.sky.framework.task.enums.RetryStrategy;
import com.sky.framework.task.enums.TaskStatus;
import com.sky.framework.task.handler.TaskExecuteResult;
import com.sky.framework.task.handler.ITaskHandler;
import com.sky.framework.task.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class ExecuteRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteRunnable.class);

    private Map<String, ITaskHandler> taskHandlerMap;
    private TaskManager taskManager;

    public ExecuteRunnable(Map<String, ITaskHandler> taskHandlerMap, TaskManager taskManager) {
        this.taskHandlerMap = taskHandlerMap;
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                PopTask popTask = taskManager.popExecutingTask(null, new Date());

                if (PopTaskResult.FAIL_SLEEP == popTask.getPopTaskResult()) {
                    ThreadUtil.safeSleep(5000);
                    continue;
                }

                if (PopTaskResult.SUCCESS == popTask.getPopTaskResult()) {
                    TaskPO taskPO = popTask.getTaskPO();
                    ITaskHandler handler = taskHandlerMap.get(taskPO.getHandler());
                    if (null == handler) {
                        LOGGER.error("任务handler不存在, handlerName={}", taskPO.getHandler());
                        taskManager.deleteTask(null, taskPO.getTaskKey());
                        continue;
                    }

                    // 执行任务
                    executeTask(handler, taskPO);
                }
            } catch (Exception e) {
                LOGGER.error("任务执行出错.", e);
                ThreadUtil.safeSleep(5000);
            }
        }
    }

    private void executeTask(ITaskHandler handler, TaskPO taskPO) {
        if (null == taskPO.getFirstTime()) {
            taskPO.setFirstTime(new Date());
        }
        taskPO.setRetriedTimes(taskPO.getRetriedTimes() + 1);

        TaskPO copyTaskPO = TaskPOBuilder.copy(taskPO);
        TaskExecuteResult result = null;
        try {
            result = handler.execute(copyTaskPO, null);
        } catch (Exception e) {
            LOGGER.error("执行任务出错,executing queque process error. taskPO={}", JSON.toJSONString(taskPO), e);
            exceptionRetryTask(taskPO);
            return;
        }

        switch (result.getTaskResult()) {
            case SUCCESS:
                successTask(result, taskPO);
                break;
            case FAIL:
                failTask(result, taskPO);
                break;
            case RETRY:
                retryTask(result, taskPO);
                break;
            case MOVE:
                break;
        }
    }

    private void successTask(TaskExecuteResult result, TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.SUCCESSFUL.getValue());
        taskPO.setProgress(result.getProgress());

        taskManager.successTask(taskPO);
    }

    private void failTask(TaskExecuteResult result, TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.FAILED.getValue());
        taskPO.setProgress(result.getProgress());

        taskManager.failTask(taskPO);
    }

    private void retryTask(TaskExecuteResult result, TaskPO taskPO) {
        taskPO.setProgress(result.getProgress());
        exceptionRetryTask(taskPO);
    }

    private void exceptionRetryTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateLastTime(taskPO, current);

        taskPO.setNextTime(DateUtil.addMiliSeconds(taskPO.getNextTime(), taskPO.getRetryInterval()));

        if (taskPO.getRetryStrategy() == RetryStrategy.NORMAL.getValue() && taskPO.getRetriedTimes() >= taskPO.getMaxRetryTimes()) {
            taskPO.setTaskStatus(TaskStatus.MORE_RETRY_FAILED.getValue());
            taskManager.failTask(taskPO);
        } else {
            taskPO.setTaskStatus(TaskStatus.RETRYING.getValue());
            taskManager.retryTask(taskPO);
        }
    }

}
