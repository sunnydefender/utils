package com.sky.framework.task.enums;

import com.sky.framework.task.mybatis.IntegerValuedEnum;

public enum TaskResult implements IntegerValuedEnum {
    SUCCESS(1),
    FAIL(2),
    RETRY(3),
    /** 迁移到其他队列 */
    MOVE(4),
    ;

    private int value;

    private TaskResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return 0;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
