package com.sky.framework.task.handler;

public interface IQuartzHandler {
    /**
     * 注意taskPO不可变
     * @return
     */
    void execute(Object params);
}
