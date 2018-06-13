package com.sky.framework.common.exception;

/**
 * Created by easyfun on 2018/4/28.
 */
public interface ErrorCode {
    /** 资金系统成功状态码 */
    public static final String SUCCESS = "SUCCESS";

    /** 资金系统失败状态码 */
    public static final String FAIL = "FAIL";

    /** 资金系统已受理状态码 */
    public static final String ACCEPTED = "ACCEPTED";

    /** 资金系统未知状态码 */
    public static final String UNKNOWN = "UNKNOWN";

    String getFailCode();
}
