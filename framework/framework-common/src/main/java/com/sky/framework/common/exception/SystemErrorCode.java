package com.sky.framework.common.exception;

/**
 * Created by easyfun on 2018/4/28.
 */
public enum SystemErrorCode implements ErrorCode {
    /**
     * 系统异常
     */
    systemException("MSSE00000000"),
    ;

    private String errorCode;

    private SystemErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getFailCode() {
        return errorCode;
    }
}
