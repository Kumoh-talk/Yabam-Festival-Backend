package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gateway.jwt.JwtHandler;
import com.gateway.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
	private final JwtProperties jwtProperties;

	@Bean
	public JwtHandler jwtHandler() {
		return new JwtHandler(jwtProperties);
	}
}
