package com.sky.framework.task.enums;

import com.sky.framework.task.mybatis.IntegerValuedEnum;

/**
 * Created by easyfun on 2018/3/27.
 */
public enum TaskStatus implements IntegerValuedEnum {
    ACCEPTED(1),
    RETRYING(2),
    PAUSED(3),
    CANCELLED(4),
    SUCCESSFUL(5),
    FAILED(6),
    /** 重试次数失败 */
    MORE_RETRY_FAILED(7)
    ;

    private TaskStatus(int value) {
        this.value = value;
    }

    private int value;

    @Override
    public int getValue() {
        return value;
    }

    public static TaskStatus buildTaskStatus(String name) {
        if (name.equals(ACCEPTED.name())) {
            return ACCEPTED;
        }
        if (name.equals(RETRYING.name())) {
            return RETRYING;
        }
        if (name.equals(PAUSED.name())) {
            return PAUSED;
        }
        if (name.equals(CANCELLED.name())) {
            return CANCELLED;
        }
        if (name.equals(SUCCESSFUL.name())) {
            return SUCCESSFUL;
        }
        if (name.equals(FAILED.name())) {
            return FAILED;
        }
        if (name.equals(MORE_RETRY_FAILED)) {
            return MORE_RETRY_FAILED;
        }
        return null;
    }
}
