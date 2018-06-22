package com.sky.framework.task;

import org.springframework.stereotype.Component;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@Component
@Inherited
public @interface QuartzHandler {
//    public String namespace() default "prd";

    /**
     * handler名称
     * @return
     */
    public String name() default "quartz";

    public long initialDelay() default 0;

    public long delay() default 5000;

    public TimeUnit unit() default TimeUnit.MILLISECONDS;
}