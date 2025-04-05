package com.application.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.application.global.interceptor.AuthorizationInterceptor;
import com.application.global.interceptor.DeserializingUserPassportInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final DeserializingUserPassportInterceptor deserializingUserPassportInterceptor;
	private final AuthorizationInterceptor authorizationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(deserializingUserPassportInterceptor)
			.addPathPatterns("/**");
		registry.addInterceptor(authorizationInterceptor)
			.addPathPatterns("/**");
	}
}
