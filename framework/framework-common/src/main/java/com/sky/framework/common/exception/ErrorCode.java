package com.sky.framework.common.exception;

/**
 * Created by easyfun on 2018/4/28.
 */
public interface ErrorCode {
    /** 成功状态码 */
    public static final int SUCCESS = 98;

    /** 失败状态码 */
    public static final int FAIL = 99;

    /** 已受理状态码 */
    public static final int ACCEPTED = 1;

    String getFailCode();
}
