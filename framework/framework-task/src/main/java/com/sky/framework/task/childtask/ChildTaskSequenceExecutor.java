package com.sky.framework.task.childtask;

import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.enums.TaskResult;
import com.sky.framework.task.handler.TaskExecuteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChildTaskSequenceExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChildTaskSequenceExecutor.class);

    public static TaskExecuteResult execute(ChildTaskContent content, TaskPO taskPO) {
        TaskExecuteResult result = TaskExecuteResult.fail(taskPO.getProgress());

        for (IChildTaskHandler childTask : content.getChildTasks()) {
            try {
                result = childTask.execute(content, taskPO);
                taskPO.setProgress(result.getProgress());

                if (result.getTaskResult() != TaskResult.SUCCESS) {
                    return result;
                }
            } catch (Exception e) {
                LOGGER.error("执行子任务异常. childTask={}. ", childTask.getClass().getSimpleName(), e);
                return TaskExecuteResult.retry(taskPO.getProgress());
            }
        }
        return result;
    }
}
