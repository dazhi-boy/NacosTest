package com.dazhi.nacos.naming.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NamingConfig {

    @Bean
    public FilterRegistrationBean distroFilterRegistration() {
        FilterRegistrationBean<DistroFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(distroFilter());
        registration.addUrlPatterns("/v1/ns/*");
        registration.setName("distroFilter");
        registration.setOrder(6);
        return registration;
    }

    @Bean
    public DistroFilter distroFilter() {
        return new DistroFilter();
    }
}
