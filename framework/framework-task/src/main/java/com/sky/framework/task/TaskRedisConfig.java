package com.sky.framework.task;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by easyfun on 2018/3/27.
 */
@Configuration
public class TaskRedisConfig {
    /* 单机 */
    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.password:}")
    private String redisPassword;

    /* 集群 */
    @Value("${redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${redis.cluster.timeOut:2000}")
    private int timeOut;
    @Value("${redis.cluster.max-redirects:8}")
    private int maxRedirects;

    /* 连接池 */
    @Value("${redis.maxIdle}")
    private int maxIdle;
    @Value("${redis.maxTotal}")
    private int maxTotal;
    @Value("${redis.maxWait}")
    private int maxWait;
    @Value("#{new Boolean('${redis.testOnBorrow}')}")
    private boolean testOnBorrow;

    private RedisClusterConfiguration getClusterConfiguration() {
        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeOut", timeOut);
        source.put("spring.redis.cluster.max-redirects", maxRedirects);

        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
    }

    private JedisPoolConfig getPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }

    private RedisConnectionFactory getRedisConnectionFactory() {
        JedisConnectionFactory factory = null;
        if (!StringUtils.isEmpty(clusterNodes)) {
            factory = new JedisConnectionFactory(getClusterConfiguration(), getPoolConfig());
        } else {
            factory = new JedisConnectionFactory(getPoolConfig());
            factory.setHostName(redisHost);
            factory.setPort(redisPort);
            if (!StringUtils.isBlank(redisPassword)) {
                factory.setPassword(redisPassword);
            }
            factory.setTimeout(timeOut);
        }
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public StringRedisTemplate taskRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(getRedisConnectionFactory());
        template.setConnectionFactory(getRedisConnectionFactory());
        return template;
    }
}
