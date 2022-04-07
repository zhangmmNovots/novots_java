package com.weilaios.iqxceqhnhubt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {



//添加拦截器
@Override
public void addInterceptors(InterceptorRegistry registry) {
}

//解决跨域问题
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
    .allowedOrigins("*")
    .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
    .allowCredentials(true).maxAge(3600);
    }
}
