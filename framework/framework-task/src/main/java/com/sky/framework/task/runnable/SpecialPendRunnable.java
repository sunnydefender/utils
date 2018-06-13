package com.sky.framework.task.runnable;

import com.sky.framework.task.TaskManager;
import com.sky.framework.task.TaskRedisKey;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.entity.builder.TaskPOBuilder;
import com.sky.framework.task.enums.TaskStatus;
import com.sky.framework.task.util.TaskRedisLock;
import com.sky.framework.task.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class SpecialPendRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialPendRunnable.class);

    private TaskManager taskManager;
    private TaskRedisLock taskRedisLock;
    private String handler = TaskRedisKey.TASK_EXECUTING_SPECIAL;

    public SpecialPendRunnable(TaskManager taskManager, TaskRedisLock taskRedisLock) {
        this.taskManager = taskManager;
        this.taskRedisLock = taskRedisLock;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            boolean lock = false;
            String lockId = taskRedisLock.buildSpecialTaskLockId(TaskRedisLock.LOCK_TASK_PENDING_SPECIAL);
            try {
                lock = taskRedisLock.lockPendingTask(lockId);
                if (false == lock) {
                    LOGGER.debug("获取t_task:special:pending:zset执行权失败");
                    continue;
                }

                TaskPO taskPO = taskManager.popPendingTask(handler, new Date());

                if (null == taskPO) {
                    ThreadUtil.safeSleep(5000);
                } else {
                    // 执行任务
                    executeTask(taskPO);
                }
            } catch (Exception e) {
                LOGGER.error("任务执行出错.", e);
            } finally {
                if (lock) {
                    taskRedisLock.unlockPendingTask(lockId);
                } else {
                    ThreadUtil.safeSleep(5000);
                }
            }
        }
     }

    private void executeTask(TaskPO taskPO) {
        if (taskPO.getTaskStatus() == TaskStatus.ACCEPTED.getValue() || taskPO.getTaskStatus() == TaskStatus.RETRYING.getValue()) {
            // 超时补任务
            timeoutSupplyRetryTask(taskPO);
            return;
        }

        if (taskPO.getTaskStatus() == TaskStatus.PAUSED.getValue() || taskPO.getTaskStatus() == TaskStatus.CANCELLED.getValue()) {
            // TODO
            return;
        }

        if (taskPO.getTaskStatus() == TaskStatus.SUCCESSFUL.getValue()) {
            successFinishTask(taskPO);
            return;
        }
        if (taskPO.getTaskStatus() == TaskStatus.FAILED.getValue()) {
            failFinishTask(taskPO);
            return;
        }
        if (taskPO.getTaskStatus() == TaskStatus.MORE_RETRY_FAILED.getValue()) {
            moreRetryFailFinishTask(taskPO);
            return;
        }
    }

    private void successFinishTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.SUCCESSFUL.getValue());

        taskManager.finishTask(taskPO);
    }

    private void moreRetryFailFinishTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.MORE_RETRY_FAILED.getValue());

        taskManager.finishTask(taskPO);
    }

    private void failFinishTask(TaskPO taskPO) {
        Date current = new Date();
        TaskPOBuilder.updateDoneTime(taskPO, current);
        taskPO.setTaskStatus(TaskStatus.FAILED.getValue());

        taskManager.finishTask(taskPO);
    }

    private void timeoutSupplyRetryTask(TaskPO taskPO) {
        Date current = new Date();
        taskPO.setNextTime(current);
        taskPO.setUpdatedTime(current);
        taskManager.timeoutSupplyRetryTask(taskPO);
    }

}
