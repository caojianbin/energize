package com.showtime.serviceenergize.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * HttpClient
 * </p>
 *
 * @author cjb
 * @since 14:46
 */
public class HttpClient {
    @Bean
    public RestTemplate restTemplate() {
        //复杂构造函数的使用
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);// 设置超时
        requestFactory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
