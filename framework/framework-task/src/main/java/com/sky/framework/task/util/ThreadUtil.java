package com.sky.framework.task.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/3/27.
 */
public class ThreadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtil.class);

    public static void safeSleep(long milis) {
        try {
            // 获取不到新的任务则休息5秒钟
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            LOGGER.error("任务终止.", e);
            Thread.currentThread().interrupt();
        }
    }
}
