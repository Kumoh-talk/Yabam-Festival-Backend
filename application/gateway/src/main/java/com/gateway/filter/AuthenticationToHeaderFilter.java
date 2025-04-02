package com.gateway.filter;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.gateway.jwt.JwtAuthentication;

import reactor.core.publisher.Mono;

public class AuthenticationToHeaderFilter implements WebFilter {
	private final ServerSecurityContextRepository securityContextRepository;

	public AuthenticationToHeaderFilter(ServerSecurityContextRepository securityContextRepository) {
		this.securityContextRepository = securityContextRepository;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return securityContextRepository.load(exchange)
			.map(SecurityContext::getAuthentication)
			.flatMap(authentication -> {
				JwtAuthentication jwtAuthentication = (JwtAuthentication)authentication;
				String userId = String.valueOf(jwtAuthentication.userId()); // 예시: 사용자 ID
				String authorities = jwtAuthentication.role().name(); // 예시: 권한 정보

				// ServerWebExchange에 헤더 추가
				exchange.getRequest().getHeaders().add("X-User-Id", userId);
				exchange.getRequest().getHeaders().add("X-Authorities", authorities);

				return chain.filter(exchange);
			})
			.switchIfEmpty(chain.filter(exchange));
	}
}
