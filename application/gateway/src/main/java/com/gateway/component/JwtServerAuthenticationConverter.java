package com.gateway.component;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.gateway.jwt.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

	private static final String BEARER = "Bearer ";

	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		return Mono.justOrEmpty(
				exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
			.filter(header -> header.startsWith(BEARER))
			.map(header -> header.substring(BEARER.length()))
			.map(JwtAuthenticationToken::new)
			.defaultIfEmpty(new JwtAuthenticationToken(null))
			.map(Authentication.class::cast);
	}
}
