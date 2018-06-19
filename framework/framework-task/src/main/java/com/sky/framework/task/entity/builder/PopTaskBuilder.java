package com.sky.framework.task.entity.builder;

import com.sky.framework.task.entity.PopTask;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.enums.PopTaskResult;

/**
 * Created by easyfun on 2018/6/19.
 */
public class PopTaskBuilder {
    public static PopTask build(TaskPO taskPO, PopTaskResult popTaskResult) {
        PopTask popTask = new PopTask();
        popTask.setTaskPO(taskPO);
        popTask.setPopTaskResult(popTaskResult);
        return popTask;
    }
}
