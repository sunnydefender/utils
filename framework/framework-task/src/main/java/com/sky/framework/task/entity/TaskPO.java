package com.sky.framework.task.entity;

import java.util.Date;

/**
 * Created by easyfun on 2018/3/27.
 */
public class TaskPO {
    private long id;

    private String taskKey;

    private String param;

    private String handler;

    private String business;

    /** 重试策略 */
    private int retryStrategy;

    /** 任务类型 */
    private int taskMode;

    /** 重试间隔ms */
    private int retryInterval;

    /** 最大重试次数 */
    private int maxRetryTimes;

    /** 执行状态 */
    private int taskStatus;

    /** 子任务执行进度 */
    private int progress;

    /** 已重试次数 */
    private int retriedTimes;

    /** 创建时间 */
    private Date createdTime;

    /** 首次执行时间 */
    private Date firstTime;

    /** 最近执行时间 */
    private Date lastTime;

    /** 下次执行时间 */
    private Date nextTime;

    /** 完成时间 */
    private Date doneTime;

    /** 更新时间 */
    private Date updatedTime;

    /** 插入db时间 */
    private Date insertedTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getRetryStrategy() {
        return retryStrategy;
    }

    public void setRetryStrategy(int retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public int getRetriedTimes() {
        return retriedTimes;
    }

    public void setRetriedTimes(int retriedTimes) {
        this.retriedTimes = retriedTimes;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Date getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }

    public int getTaskMode() {
        return taskMode;
    }

    public void setTaskMode(int taskMode) {
        this.taskMode = taskMode;
    }
}
