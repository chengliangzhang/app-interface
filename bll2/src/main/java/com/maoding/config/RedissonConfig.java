package com.maoding.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Created by Wuwq on 2017/2/6.
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private Environment env;

    @Value("${redisson.configPath}")
    private String config;


    @Value("${redisson.configPath_session}")
    private String config_session;


    @Bean(name = "redissonClient_corp", destroyMethod = "shutdown")
    public RedissonClient redissonClient_corp() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource rc = resolver.getResource(config);
        Config config = Config.fromJSON(rc.getInputStream());
        RedissonClient client = Redisson.create(config);
        return client;
    }

    @Bean(name = "redissonClient_session", destroyMethod = "shutdown")
    public RedissonClient redissonClient_session() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource rc = resolver.getResource(config_session);
        Config config = Config.fromJSON(rc.getInputStream());
        RedissonClient client = Redisson.create(config);
        return client;
    }
}
