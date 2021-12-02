package com.showtime.serviceenergize.config;

import com.showtime.serviceenergize.serializer.MyStringRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
* @ClassName RedisConfig
* @Description : Redis注入服务
* @Author : jlp
* @Date: 2019/12/17 20:51
*/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        MyStringRedisSerializer myStringRedisSerializer=new MyStringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(myStringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.setHashValueSerializer(myStringRedisSerializer);
        return template;
    }

}

