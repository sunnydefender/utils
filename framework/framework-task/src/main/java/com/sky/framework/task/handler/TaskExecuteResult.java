package com.sky.framework.task.handler;

import com.sky.framework.task.enums.TaskResult;

public class TaskExecuteResult {
    private TaskResult taskResult;
    private int progress;

    public TaskExecuteResult() {
        taskResult = TaskResult.SUCCESS;
        progress = 0;
    }

    public TaskExecuteResult(TaskResult taskResult, int progress) {
        this.taskResult = taskResult;
        this.progress = progress;
    }

    public TaskResult getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public static TaskExecuteResult success() {
        return new TaskExecuteResult(TaskResult.SUCCESS, 0);
    }

    public static TaskExecuteResult success(int progress) {
        return new TaskExecuteResult(TaskResult.SUCCESS, progress);
    }

    public static TaskExecuteResult fail() {
        return new TaskExecuteResult(TaskResult.FAIL, 0);
    }

    public static TaskExecuteResult fail(int progress) {
        return new TaskExecuteResult(TaskResult.FAIL, progress);
    }

    public static TaskExecuteResult retry() {
        return new TaskExecuteResult(TaskResult.RETRY, 0);
    }

    public static TaskExecuteResult retry(int progress) {
        return new TaskExecuteResult(TaskResult.RETRY, progress);
    }

}
