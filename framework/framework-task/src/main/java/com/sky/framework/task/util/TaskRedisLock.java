package com.sky.framework.task.util;

import com.sky.framework.task.TaskRedisKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.util.SafeEncoder;

import java.util.Date;

/**
 * Created by easyfun on 2018/5/17.
 */
@Component
public class TaskRedisLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRedisLock.class);

    public static final String LOCK_TASK_PENDING = "lock:" + TaskRedisKey.TASK_PENDING;
    public static final String LOCK_TASK_PENDING_SPECIAL = "lock:" + TaskRedisKey.TASK_PENDING_SPECIAL;

    public static final long LOCK_TASK_PENDING_TIMEOUT = 300L;

    @Autowired
    private StringRedisTemplate taskRedisTemplate;

    /**
     * 5分钟超时
     * @param key
     * @return
     */
    public boolean lockPendingTask(String key) {
        try {
            return set(key, DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS), "NX", "EX", LOCK_TASK_PENDING_TIMEOUT);
        } catch (Exception e) {
            LOGGER.error("加锁t_task:pending:zset失败, key={}", key);
            return false;
        }
    }

    public void unlockPendingTask(String key) {
        try {
            taskRedisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("解锁t_task:pending:zset失败, key={}", key);
        }
    }

    public boolean lockTimeout(String key, long timeout) {
        if (StringUtils.isBlank(key)) {
            return false;
        }

        String lockKey = buildLockTimeoutKey(key);
        try {
            return set(lockKey, DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS), "NX", "EX", timeout);
        } catch (Exception e) {
            LOGGER.error("加锁lock:timeout失败, key={}, lockKey={}", key, lockKey);
            return false;
        }
    }

    public void unlock(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        String lockKey = buildLockTimeoutKey(key);
        try {
            taskRedisTemplate.delete(lockKey);
        } catch (Exception e) {
            LOGGER.error("解锁失败, key={}, lockKey={}", key, lockKey);
        }
    }

    public String buildSpecialTaskLockId(String handler) {
        return LOCK_TASK_PENDING + ":" + handler;
    }

    /**
     * EX seconds -- Set the specified expire time, in seconds.
     * PX milliseconds -- Set the specified expire time, in milliseconds.
     * NX -- Only set the key if it does not already exist.
     * XX -- Only set the key if it already exist.
     * @param key
     * @param value
     * @param nxxx
     * @param expx
     * @param time
     * @return
     */
    public boolean set(final String key, final String value, final String nxxx, final String expx, final long time) {
        Object result = taskRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = taskRedisTemplate.getStringSerializer();
                Object executeReturn = connection.execute("set", new byte[][]{
                        serializer.serialize(key), serializer.serialize(value),
                        serializer.serialize(nxxx), serializer.serialize(expx),
                        SafeEncoder.encode(String.valueOf(time))

                });
                return null == executeReturn ? false : true;
            }
        });
        return (Boolean) result == true ? true : false;
    }

    private String buildLockTimeoutKey(String key) {
        return "lock:timeout:" + key;
    }
}
