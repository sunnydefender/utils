package com.sky.framework.common.exception;

/**
 * Created by easyfun on 2018/4/28.
 */
public class SystemException extends BaseException {
    private static final long serialVersionUID = 6381907371199421313L;

    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SystemException(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public SystemException(ErrorCode errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }
}
