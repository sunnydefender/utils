package com.sky.framework.task;

import com.sky.framework.task.enums.ExecuteStrategy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 专用任务注解
 */

@Retention(RUNTIME)
@Target(TYPE)
@Component
@Inherited
public @interface SpecialTaskHandler {
//    public String namespace() default "prd";


    /**
     * handler名称
     * @return
     */
    public String name() default "specialName";

    /**
     * handler线程数
     * @return
     */
    public int threadCount() default 1;

    /**
     * handler线程sleep毫秒数
     * @return
     */
    public int threadSleepMilis() default 5000;

    /**
     * 线程执行模式: 1-等间隔执行一个任务; 其他-等间隔执行到任务获取失败
     * @return
     */
    public ExecuteStrategy executeStrategy() default ExecuteStrategy.NORMAL;
}