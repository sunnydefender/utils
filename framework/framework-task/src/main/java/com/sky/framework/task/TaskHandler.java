package com.sky.framework.task;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RUNTIME)
@Target(TYPE)
@Component
@Inherited
public @interface TaskHandler {
//    public String namespace() default "prd";


    /**
     * handler名称
     * @return
     */
     public String name() default "name";

}