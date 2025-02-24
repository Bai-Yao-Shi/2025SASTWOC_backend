package org.example.sastwoc.config;

import org.example.sastwoc.interceptor.HttpInterceptor;
import org.example.sastwoc.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
   @Autowired
   private LoginInterceptor loginInterceptor;

   @Autowired
   private HttpInterceptor httpInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      //登录拦截器
      registry.addInterceptor(loginInterceptor)
              .excludePathPatterns("/login")
              .excludePathPatterns("/register")
              .excludePathPatterns("/user/userdetail");

      //网页拦截器
      registry.addInterceptor(httpInterceptor)
              .excludePathPatterns("/login")
              .excludePathPatterns("/register")
              .excludePathPatterns("/user/userdetail");

   }
}