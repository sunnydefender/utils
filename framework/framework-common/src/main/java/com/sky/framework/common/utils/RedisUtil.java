package com.sky.framework.common.utils;

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
