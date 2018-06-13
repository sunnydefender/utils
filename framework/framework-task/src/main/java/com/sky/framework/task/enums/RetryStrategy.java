package com.sky.framework.task.enums;


import com.sky.framework.task.mybatis.IntegerValuedEnum;

/**
 * Created by easyfun on 2018/3/27.
 */
public enum RetryStrategy implements IntegerValuedEnum {
    NORMAL(1),
    ;

    private int value;

    private RetryStrategy(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static RetryStrategy buildRetryStrategy(String name) {
        if (name.equals(NORMAL.name())) {
            return NORMAL;
        }
        return null;
    }
}
