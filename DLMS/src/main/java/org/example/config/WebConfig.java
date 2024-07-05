package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") //允许访问的路径
                        .allowedOriginPatterns("http://localhost:*") //来源
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH") //允许请求的方法
                        .allowCredentials(true) //是否允许证书 不再默认开启
                        .allowedHeaders("*") //允许头部设置
                        .maxAge(3600); //预检请求的有效期，单位为秒。设置maxage，可以避免每次都发出预检请求
            }
        };
    }
}
