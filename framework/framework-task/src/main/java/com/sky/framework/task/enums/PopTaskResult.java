package com.sky.framework.task.enums;

import com.sky.framework.task.mybatis.IntegerValuedEnum;

/**
 * Created by easyfun on 2018/6/19.
 */
public enum PopTaskResult implements IntegerValuedEnum {
    SUCCESS(1),

    FAIL_SLEEP(2),

    TRY_NEXT(3),
    ;

    private int value;

    PopTaskResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
