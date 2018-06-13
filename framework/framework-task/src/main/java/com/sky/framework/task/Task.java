package com.sky.framework.task;


import com.sky.framework.task.enums.RetryStrategy;
import com.sky.framework.task.enums.TaskMode;

import java.util.Date;

/**
 * Created by easyfun on 2018/3/27.
 */
public class Task {
    /** 默认最大重试次数 */
    public static final int DEFAULT_MAX_RETRY_TIMES = 15;
    /** 默认重试间隔60s */
    public static final int DEFAULT_RETRY_INTERVAL = 60 * 1000;

    private String taskKey;
    private String param;
    private String handler;
    private String business;
    private RetryStrategy retryStrategy;
    private TaskMode taskMode = TaskMode.NORMAL;
    /** 最大重试次数 */
    private int maxRetryTimes = DEFAULT_MAX_RETRY_TIMES;
    /** 重试时间间隔ms */
    private int retryInterval = DEFAULT_RETRY_INTERVAL;

    private Date nextTime = new Date();

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public RetryStrategy getRetryStrategy() {
        return retryStrategy;
    }

    public void setRetryStrategy(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        if (retryInterval < 0) {
            this.retryInterval = DEFAULT_RETRY_INTERVAL;
        }
        this.retryInterval = retryInterval;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public TaskMode getTaskMode() {
        return taskMode;
    }

    public void setTaskMode(TaskMode taskMode) {
        this.taskMode = taskMode;
    }
}
