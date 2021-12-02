package com.showtime.serviceenergize.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

/**
 * <p>
 * WebMvcConfigurer
 * </p>
 *
 * @author cjb
 * @since 16:53
 */
@SpringBootApplication
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
@Profile({"dev","test"})
public class SwaggerBootstrapUiDemoApplication implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
