package com.sky.framework.tests;

import com.sky.framework.task.TaskHandler;
import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.enums.TaskResult;
import com.sky.framework.task.handler.TaskExecuteResult;
import com.sky.framework.task.handler.TaskHandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TaskHandler
public class TestTaskHandler implements TaskHandlerInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestTaskHandler.class);

    @Override
    public TaskExecuteResult execute(TaskPO taskPO, Object params) {
        LOGGER.info("hello test task");

        TaskExecuteResult result = new TaskExecuteResult();
        result.setTaskResult(TaskResult.SUCCESSFUL);
        result.setProgress(0);
        return result;
    }
}
