package com.randream.config;


import com.randream.interceptors.AfterLoginInterceptors;
import com.randream.interceptors.FrequentInterceptors;
import com.randream.interceptors.ImgVerifyCodeInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public AfterLoginInterceptors afterLoginInterceptors() {
        return new AfterLoginInterceptors();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FrequentInterceptors()).addPathPatterns("/**"); //所有路径都被拦截
        registry.addInterceptor(new ImgVerifyCodeInterceptors()).addPathPatterns("/v1/login");
        registry.addInterceptor(afterLoginInterceptors()).addPathPatterns("/v2/*");
    }

}
