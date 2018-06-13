package com.sky.framework.task;

import com.sky.framework.task.entity.TaskPO;
import com.sky.framework.task.enums.TaskMode;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by easyfun on 2018/4/28.
 */
public class TaskRedisKey {
    public static final String TASK_INFO = "t_task:hash:info";
    public static final String TASK_PENDING = "t_task:zset:pending";
    public static final String TASK_EXECUTING = "t_task:zset:executing";
    public static final String TASK_PENDING_SPECIAL = "t_task:zset:pending:special";
    public static final String TASK_EXECUTING_SPECIAL = "t_task:zset:executing:special";

//    public static String buildKeyTaskInfo(String key) {
//        return TASK_INFO + key;
//    }

//    public static String buildSpecialKey(String key, String handler) {
//        return key + ":" + handler;
//    }

    public static String buildKeyInfo(TaskPO taskPO) {
            return TASK_INFO;
    }

    public static String buildKeyInfo(String handler) {
        return TASK_INFO;
    }

    public static String buildKeyPending(TaskPO taskPO) {
        if (taskPO.getTaskMode() == TaskMode.NORMAL.getValue()) {
            return TASK_PENDING;
        }
        return TASK_PENDING_SPECIAL;
    }

    public static String buildKeyPending(String handler) {
        if (StringUtils.isBlank(handler)) {
            return TASK_PENDING;
        }
        return TASK_PENDING_SPECIAL;
    }

    public static String buildKeyExecuting(TaskPO taskPO) {
        if (taskPO.getTaskMode() == TaskMode.NORMAL.getValue()) {
            return TASK_EXECUTING;
        }
        return TASK_EXECUTING_SPECIAL + ":" + taskPO.getHandler();
    }

    public static String buildKeyExecuting(String handler) {
        if (StringUtils.isBlank(handler)) {
            return TASK_EXECUTING;
        }
        return TASK_EXECUTING_SPECIAL + ":" + handler;
    }

}
