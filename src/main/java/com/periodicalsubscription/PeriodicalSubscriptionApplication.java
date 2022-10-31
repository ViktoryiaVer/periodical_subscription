package com.periodicalsubscription;

import com.periodicalsubscription.filter.AuthorizationFilter;
import com.periodicalsubscription.interceptor.MessageInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PeriodicalSubscriptionApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(PeriodicalSubscriptionApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
		FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthorizationFilter());
		registrationBean.addUrlPatterns("/user/*",
				"/periodical/create/*", "/periodical/update/*", "/periodical/delete/*",
				"/subscription/*", "/payment/*");
		return registrationBean;
	}

	@Bean
	public MessageInterceptor messageInterceptor(){
		return new MessageInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(messageInterceptor());
	}
}
