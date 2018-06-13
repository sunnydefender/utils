package com.sky.framework.task.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2018/3/27.
 */
public class RedisUtil {
    public static String buildRedisString(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }
}
