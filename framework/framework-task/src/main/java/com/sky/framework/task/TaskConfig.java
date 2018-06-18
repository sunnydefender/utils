package com.sky.framework.task;

import com.sky.framework.task.handler.ITaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TaskConfig {
    @Bean
    public Map<String, ITaskHandler> taskHandlerMap() {
        //TODO:map元素数小，比较原生数组，HashMap在100个元素的遍历性能
        return new HashMap<String, ITaskHandler>();
    }
}
