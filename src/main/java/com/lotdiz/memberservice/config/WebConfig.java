package com.lotdiz.memberservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173")
        .allowedHeaders("*")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS", "HEAD")
        .allowCredentials(true);
  }
}
