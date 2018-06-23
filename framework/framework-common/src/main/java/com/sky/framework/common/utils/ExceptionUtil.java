package com.sky.framework.common.utils;

/**
 * Created by easyfun on 2018/6/23.
 */
public class ExceptionUtil {
    public static String buildFailReason(Exception e) {
        String failReason = e.getMessage().length() <= 256 ? e.getMessage() : e.getMessage().substring(0, 255);
        return failReason;
    }
}
