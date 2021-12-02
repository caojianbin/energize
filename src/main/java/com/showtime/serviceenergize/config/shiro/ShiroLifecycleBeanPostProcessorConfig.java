package com.showtime.serviceenergize.config.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName : ShiroLifecycleBeanPostProcessorConfig
 * @Description :Shiro生命周期处理器
 * @Author : jlp
 * @Date: 2019-12-17 10:45
 */
public class ShiroLifecycleBeanPostProcessorConfig {
    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();

    }
}
