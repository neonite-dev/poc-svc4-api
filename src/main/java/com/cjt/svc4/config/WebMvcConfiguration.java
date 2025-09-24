package com.cjt.svc4.config;

import com.cjt.svc4.web.interceptor.CrmInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @SuppressWarnings("null")
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new CrmInterceptor())
        .addPathPatterns("/api/*");
  }

  @SuppressWarnings("null")
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**").allowedOrigins("*");
  }

  @SuppressWarnings("null")
  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webdata/image/**")
                .addResourceLocations("file:webdata/image/");
    }

  // @SuppressWarnings("null")
  // @Override
  // public void addResourceHandlers(ResourceHandlerRegistry registry) {
  //     if (!registry.hasMappingForPattern("/vueapp/**")) {
  //       registry.addResourceHandler("/vueapp/**").addResourceLocations("classpath:/static/vueapp/");
  //     }
  // }
}