package com.sky.framework.task.handler;

import com.sky.framework.task.entity.TaskPO;

public interface TaskHandlerInterface {
    /**
     * 注意taskPO不可变
     * @param taskPO
     * @return
     */
    TaskExecuteResult execute(TaskPO taskPO, Object params);
}
