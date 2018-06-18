package com.sky.framework.task;

import com.sky.framework.task.handler.IQuartzHandler;
import com.sky.framework.task.handler.ITaskHandler;
import com.sky.framework.task.util.TaskRedisLock;
import com.sky.framework.task.runnable.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Executors;

@Component
public class TaskInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskInitializer.class);

    @Value("${framework.task.execute.thread.count:1}")
    private int taskExecutingQueueThreadCount;

    @Value("${framework.task.execute.pending.start:1}")
    private int startPending;

    @Value("${framework.task.execute.special.pending.start:1}")
    private int startSpecialPending;

    @Autowired
    private TaskRedisLock taskRedisLock;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOGGER.info("=======================>开始注册通用TaskHandler列表<=======================");
        Map<String, Object> beanMap = event.getApplicationContext().getBeansWithAnnotation(TaskHandler.class);
        Map<String, ITaskHandler> taskHandlerMap = (Map<String, ITaskHandler>) event.getApplicationContext().getBean("taskHandlerMap");

        for (Object beanObject : beanMap.values()) {
            TaskHandler annotation = beanObject.getClass().getAnnotation(TaskHandler.class);
            LOGGER.info("taskHandler: handlerName={}, simpleName={}, fullName={}", annotation.name(), beanObject.getClass().getSimpleName(), beanObject.getClass().getName());
            taskHandlerMap.put(beanObject.getClass().getSimpleName(), (ITaskHandler)beanObject);
        }

        TaskManager taskManager = event.getApplicationContext().getBean("taskManager", TaskManager.class);
        //启动executing队列处理线程
        if (taskHandlerMap.size() > 0) {
            for (int i = 0; i < taskExecutingQueueThreadCount; i++) {
                new Thread(new ExecuteRunnable(taskHandlerMap, taskManager), "execute-" + i).start();
            }
        }

        //启动pending队列处理线程
        if (1 == startPending) {
            new Thread(new PendRunnable(taskManager, taskRedisLock), "pend").start();
        }

        LOGGER.info("=======================>完成注册通用TaskHandler列表<=======================");

        LOGGER.info("=======================>开始注册专用TaskHandler列表<=======================");
        Map<String, Object> specialBeanMap = event.getApplicationContext().getBeansWithAnnotation(SpecialTaskHandler.class);

        for (Object beanObject : specialBeanMap.values()) {
            SpecialTaskHandler annotation = beanObject.getClass().getAnnotation(SpecialTaskHandler.class);
            LOGGER.info("taskHandler: handlerName={}, threadCount={}, threadSleepMilis={}, executeStrategy={}, simpleName={}, fullName={}",
                    annotation.name(), annotation.threadCount(), annotation.threadSleepMilis(), annotation.executeStrategy(),
                    beanObject.getClass().getSimpleName(), beanObject.getClass().getName());
            //启动专用executing队列处理线程
            String handler = annotation.getClass().getSimpleName();
            for (int i=0; i<annotation.threadCount(); i++) {
                new Thread(new SpecialExecuteRunnable(handler, annotation.threadSleepMilis(), annotation.executeStrategy(),
                    (ITaskHandler) beanObject, taskManager), "special-execute-" + handler + "-" + i).start();
            }
        }

        //启动专用pending队列处理线程
        if (1 == startSpecialPending) {
            new Thread(new SpecialPendRunnable(taskManager, taskRedisLock), "special-pend").start();
        }
        LOGGER.info("=======================>完成注册专用TaskHandler列表<=======================");

        LOGGER.info("=======================>开始启动定时Handler列表<=======================");
        Map<String, Object> quartzBeanMap = event.getApplicationContext().getBeansWithAnnotation(QuartzHandler.class);
        for (Object beanObject : quartzBeanMap.values()) {
            QuartzHandler annotation = beanObject.getClass().getAnnotation(QuartzHandler.class);
            LOGGER.info("taskHandler: handlerName={}, initialDelay={}, delay={}, unit={}, simpleName={}, fullName={}",
                    annotation.name(), annotation.initialDelay(), annotation.delay(), annotation.unit(),
                    beanObject.getClass().getSimpleName(), beanObject.getClass().getName());
            String handler = beanObject.getClass().getSimpleName();
            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(
                    new QuartzRunnable(handler, (IQuartzHandler)beanObject),
                    annotation.initialDelay(), annotation.delay(), annotation.unit());
        }
        LOGGER.info("=======================>完成启动定时Handler列表<=======================");
    }
}
