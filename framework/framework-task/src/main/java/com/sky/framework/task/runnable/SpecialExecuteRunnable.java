package com.sky.framework.task.runnable;

import com.alibaba.fastjson.JSON;
import com.sky.framework.task.entity.PopTask;
import com.sky.framework.task.enums.PopTaskResult;
import com.sky.framework.task.util.DateUtil;
import com.sky.framework.task.TaskManager;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.entity.builder.TaskPOBuilder;
import com.sky.framework.task.enums.RetryStrategy;
import com.sky.framework.task.enums.ExecuteStrategy;
import com.sky.framework.task.enums.TaskStatus;
import com.sky.framework.task.handler.TaskExecuteResult;
import com.sky.framework.task.handler.ITaskHandler;
import com.sky.framework.task.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SpecialExecuteRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialExecuteRunnable.class);

    private ITaskHandler taskHandlerInterface;
    private TaskManager taskManager;
    private String handler;
    private int sleepMilis = 5000;
    private ExecuteStrategy executeStrategy; /* 线程执行模式: 1-等间隔执行一个任务; 其他-等间隔执行到任务获取失败 */

    public SpecialExecuteRunnable(String handler, int sleepMilis, ExecuteStrategy executeStrategy, ITaskHandler taskHandlerInterface, TaskManager taskManager) {
        this.handler = handler;
        if (sleepMilis > 0) {
            this.sleepMilis = sleepMilis;
        }
        this.executeStrategy = executeStrategy;
        this.taskHandlerInterface = taskHandlerInterface;
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                PopTask popTask = taskManager.popExecutingTask(handler, new Date());

                if (PopTaskResult.FAIL_SLEEP == popTask.getPopTaskResult()) {
                    ThreadUtil.safeSleep(sleepMilis);
                    continue;
                }

                if (PopTaskResult.SUCCESS == popTask.getPopTaskResult()) {
                    TaskPO taskPO = popTask.getTaskPO();
                    if (!handler.equals(taskPO.getHandler())) {
                        LOGGER.error("任务handler不存在, handlerName={}", taskPO.getHandler());
                        taskManager.deleteTask(handler, taskPO.getTaskKey());
                        continue;
                    }

                    // 执行任务
                    executeTask(taskPO);

                    if (ExecuteStrategy.NORMAL == executeStrategy) {
                        ThreadUtil.safeSleep(sleepMilis);
                        continue;
                    }
                }
            } catch (Exception e) {
                LOGGER.error("任务执行出错.", e);
                ThreadUtil.safeSleep(sleepMilis);
            }
        }
    }

    private void executeTask(TaskPO taskPO) {
        if (null == taskPO.getFirstTime()) {
            taskPO.setFirstTime(new Date());
        }
        taskPO.setRetriedTimes(taskPO.getRetriedTimes() + 1);

        TaskPO copyTaskPO = TaskPOBuilder.copy(taskPO);
        TaskExecuteResult result = null;
        try {
            result = taskHandlerInterface.execute(copyTaskPO, null);
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
