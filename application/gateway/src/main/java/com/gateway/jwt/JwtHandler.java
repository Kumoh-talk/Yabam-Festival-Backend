package com.gateway.jwt;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.gateway.user.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtHandler {

	public static final String USER_ID = "USER_ID";
	public static final String USER_NICKNAME = "USER_NICKNAME";
	public static final String USER_ROLE = "USER_ROLE";
	private static final long MILLI_SECOND = 1000L;

	private final SecretKey secretKey;
	private final JwtProperties jwtProperties;

	public JwtHandler(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		secretKey = new SecretKeySpec(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	// 필터에서 토큰의 상태를 검증하기 위한 메서드 exception은 사용하는 곳에서 처리
	public JwtUserClaim parseToken(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return this.convert(claims);
	}

	public JwtUserClaim convert(Claims claims) {
		return new JwtUserClaim(
			claims.get(USER_ID, Long.class),
			claims.get(USER_NICKNAME, String.class),
			Role.valueOf(claims.get(USER_ROLE, String.class))
		);
	}
}
