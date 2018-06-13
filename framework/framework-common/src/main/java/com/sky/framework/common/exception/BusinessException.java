package com.sky.framework.common.exception;

/**
 * Created by easyfun on 2018/4/28.
 */
public class BusinessException extends BaseException {
    private static final long serialVersionUID = 2485505442365026171L;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public BusinessException(ErrorCode errorCode, String errorMessage, Throwable e) {
        super(errorCode, errorMessage, e);
    }
}
