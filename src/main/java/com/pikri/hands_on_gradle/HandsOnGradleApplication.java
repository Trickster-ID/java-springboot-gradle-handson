package com.pikri.hands_on_gradle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.pikri.hands_on_gradle.middleware.BasicAuthMiddleware;

@SpringBootApplication
public class HandsOnGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandsOnGradleApplication.class, args);
	}

    // Register the BasicAuthFilter as a Bean
    @Bean
    public FilterRegistrationBean<BasicAuthMiddleware> basicAuthFilter() {
        FilterRegistrationBean<BasicAuthMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BasicAuthMiddleware());
        registrationBean.addUrlPatterns("/api/*"); // Apply to specific URL patterns, e.g., /ping
        return registrationBean;
    }
}