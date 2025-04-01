package com.gateway.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class JwtProperties {
	@Value("${jwt.secret-key}")
	private String secretKey;
	@Value("${jwt.access-token-expire-in}")
	private int accessTokenExpireIn;
	@Value("${jwt.refresh-token-expire-in}")
	private int refreshTokenExpireIn;
}
