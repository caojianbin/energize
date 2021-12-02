package com.showtime.serviceenergize.config;

import com.showtime.serviceenergize.Interceptor.EnergizeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author cjb
 *
 * @Date 2020/4/2 0002
 *
 * @Description //TODO
 */
@Configuration
public class EnergizeInterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    EnergizeInterceptor activationCodeInterceptor(){
        return new EnergizeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加token拦截器
        InterceptorRegistration tokenInterceptorRegistration = registry.addInterceptor(activationCodeInterceptor());
        tokenInterceptorRegistration.addPathPatterns("/**");
        tokenInterceptorRegistration.excludePathPatterns("/*.html","anon");
        tokenInterceptorRegistration.excludePathPatterns("/stEnergizeLog/**.do","anon");
        tokenInterceptorRegistration.excludePathPatterns("/**/*.api","anon");
        tokenInterceptorRegistration.excludePathPatterns("/doc.html","anon");
        tokenInterceptorRegistration.excludePathPatterns("/doc/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/swagger-ui.html","anon");
        tokenInterceptorRegistration.excludePathPatterns("/swagger/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/webjars/**", "anon");
        tokenInterceptorRegistration.excludePathPatterns("/swagger-resources/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/v2/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/energize/wx/pay/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/ali/pay/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/st/bg/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/stEnergizeOrder/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/st/stEnergizeCommercial/**","anon");
        tokenInterceptorRegistration.excludePathPatterns("/stProcode/**","anon");
    }
}
