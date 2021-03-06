package com.maoding.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;

/**
 * Created by Wuwq on 2016/10/11.
 */
@Configuration
@PropertySource(value = {"classpath:config/redis.properties"})
public class RedisConfig {
    @Value("${redis.host}")
    public String Host;

    @Value("${redis.port}")
    public int Port;

    @Value("${redis.timeout}")
    public int Timeout;

    @Value("${redis.password}")
    public String Password;


    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(Host);
        factory.setPort(Port);
        factory.setTimeout(Timeout); //设置连接超时时间
        factory.setPassword(Password);
        return factory;
    }

    @Bean
    public JedisPoolConfig redisJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(300);
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        return  jedisPoolConfig;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(10); //设置key-value超时时间
        return cacheManager;
    }
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template;
        template = new StringRedisTemplate(factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        return template;
    }

    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
    }

    @Bean
    public JedisPool getJedisPool(JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool=new JedisPool(jedisPoolConfig,Host,Port,Timeout,Password);
        return jedisPool;
    }
}
