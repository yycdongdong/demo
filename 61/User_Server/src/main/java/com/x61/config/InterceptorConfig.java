package com.x61.config;


import com.x61.interceptors.FrequentInterceptors;
import com.x61.interceptors.ImgVerifyCodeInterceptors;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //@Bean
    //public AfterLoginInterceptors afterLoginInterceptors() {
    //    return new AfterLoginInterceptors();
    //}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FrequentInterceptors()).addPathPatterns("/**"); //所有路径都被拦截
        registry.addInterceptor(new ImgVerifyCodeInterceptors()).addPathPatterns("/v1/login");
        //registry.addInterceptor(afterLoginInterceptors()).addPathPatterns("/v2/*");
    }

}
