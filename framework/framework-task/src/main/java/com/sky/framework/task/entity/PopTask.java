package com.sky.framework.task.entity;

import com.sky.framework.task.enums.PopTaskResult;

/**
 * Created by easyfun on 2018/6/19.
 */
public class PopTask {
    private PopTaskResult popTaskResult;

    private TaskPO taskPO;

    public PopTaskResult getPopTaskResult() {
        return popTaskResult;
    }

    public void setPopTaskResult(PopTaskResult popTaskResult) {
        this.popTaskResult = popTaskResult;
    }

    public TaskPO getTaskPO() {
        return taskPO;
    }

    public void setTaskPO(TaskPO taskPO) {
        this.taskPO = taskPO;
    }
}
