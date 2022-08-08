package com.sparta.matchgi.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                //.allowedOrigins("http://localhost:3000")
                //.allowedOrigins("http://togetherheyyo.s3-website.ap-northeast-2.amazonaws.com/")
                .allowedMethods("*")
                .exposedHeaders("Authorization");
    }
}