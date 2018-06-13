package com.sky.framework.task.enums;

import com.sky.framework.task.mybatis.IntegerValuedEnum;

/**
 * Created by easyfun on 2018/3/27.
 */
public enum TaskMode implements IntegerValuedEnum {
    NORMAL(1),
    SPECIAL(2),
    ;

    private TaskMode(int value) {
        this.value = value;
    }

    private int value;

    @Override
    public int getValue() {
        return value;
    }

}
