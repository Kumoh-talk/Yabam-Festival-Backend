package com.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.exception.handler.GlobalExceptionHandler;

@AutoConfiguration
public class GlobalExceptionHandlerAutoConfiguration {
	@Bean
	public GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}
}
