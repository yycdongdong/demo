package com.x61;


import com.x61.filters.BodyReaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan("com.x61.filters")
public class SpringAPP {
    public static void main(String[] args) {
        SpringApplication.run(SpringAPP.class, args);
        System.out.println("ToCheckout");
    }

    @Bean
    public FilterRegistrationBean<BodyReaderFilter> Filters() {
        FilterRegistrationBean<BodyReaderFilter> registrationBean = new FilterRegistrationBean<BodyReaderFilter>();
        registrationBean.setFilter(new BodyReaderFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("bodyReaderFilter");
        return registrationBean;
    }
}
