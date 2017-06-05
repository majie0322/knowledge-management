package com.huizhi.management.conf;

import com.huizhi.management.util.RedisUtilsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * redis配置
 * @author onlyo
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    @Autowired
    private Environment env;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(env.getProperty("spring.redis.host").trim());
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("spring.redis.port").trim()));
        jedisConnectionFactory.setPassword(env.getProperty("spring.redis.password").trim());
        jedisConnectionFactory.setDatabase(Integer.parseInt(env.getProperty("spring.redis.database").trim()));
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);

        return jedisConnectionFactory;
    }

    /**
     * 注入的是自己封装的redis模板
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisUtilsTemplate redisTemplate(){
        RedisUtilsTemplate redisTemplate = new RedisUtilsTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
