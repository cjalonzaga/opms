package com.opms.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/superadmin/dashboard").setViewName("dashboard");
        registry.addViewController("/admin/dashboard").setViewName("dashboard");
        registry.addViewController("/student/dashboard").setViewName("dashboard");
        registry.addViewController("/parent/dashboard").setViewName("dashboard");
    }
}
