package com.sky.framework.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by easyfun on 2018/3/31.
 */
@Component
public class RedisTaskUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTaskUtil.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void addTask(String key, List<String> values) {
        for (String v : values) {
            redisTemplate.opsForList().rightPush(key, v);
        }
    }

    public void addTask(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public String popTask(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public long getTaskCount(String key) {
        return redisTemplate.opsForList().size(key);
    }


}
