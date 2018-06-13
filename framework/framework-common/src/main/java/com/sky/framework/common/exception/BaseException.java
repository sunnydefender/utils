package com.sky.framework.common.exception;

/**
 * Created by easyfun on 2018/4/28.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    private String errorMessage;

    public BaseException(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public BaseException(ErrorCode errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public BaseException(ErrorCode errorCode, String errorMessage, Throwable e) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        super.initCause(e);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
