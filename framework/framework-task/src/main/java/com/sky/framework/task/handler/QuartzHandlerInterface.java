package com.sky.framework.task.handler;

public interface QuartzHandlerInterface {
    /**
     * 注意taskPO不可变
     * @return
     */
    void execute(Object params);
}
