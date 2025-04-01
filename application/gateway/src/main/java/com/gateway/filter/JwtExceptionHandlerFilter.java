package com.gateway.filter;

import static com.response.ResponseUtil.*;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.exception.JwtAuthenticationException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class JwtExceptionHandlerFilter implements WebFilter {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return chain.filter(exchange)
			.onErrorResume(JwtAuthenticationException.class, e -> {
				log.error("JwtAuthenticationException: {}", e.getErrorCode().getMessage());
				return handleServiceException(exchange.getResponse(), e);
			});
	}

	private Mono<Void> handleServiceException(ServerHttpResponse response, JwtAuthenticationException e) {
		response.setStatusCode(e.getErrorCode().getStatus());
		response.getHeaders().setContentType(MediaType.valueOf("application/json; charset=UTF-8"));

		// 에러 응답 생성
		String errorResponse;
		try {
			errorResponse = objectMapper.writeValueAsString(createFailureResponse(e.getErrorCode()));
		} catch (JsonProcessingException jsonProcessingException) {
			errorResponse = "{\"error\":\"Internal Server Error\"}";
		}

		DataBuffer dataBuffer = response.bufferFactory().wrap(errorResponse.getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(dataBuffer));
	}
}
