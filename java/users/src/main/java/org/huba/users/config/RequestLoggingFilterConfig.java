package org.huba.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class RequestLoggingFilterConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private LoggingInterceptor loggingInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loggingInterceptor)
//                .addPathPatterns("/**/");
//    }
//}