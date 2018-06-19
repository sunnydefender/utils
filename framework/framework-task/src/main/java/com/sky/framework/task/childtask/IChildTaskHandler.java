package com.sky.framework.task.childtask;

import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.handler.TaskExecuteResult;

public interface IChildTaskHandler {
    TaskExecuteResult execute(ChildTaskContent content, TaskPO taskPO);
}
