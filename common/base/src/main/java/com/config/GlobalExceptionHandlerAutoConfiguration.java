package com.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import com.exception.handler.GlobalExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@AutoConfiguration
public class GlobalExceptionHandlerAutoConfiguration {
	@Bean
	@ConditionalOnClass(HttpServletRequest.class)
	public GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}
}
