package com.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.client.RestTemplate;

import com.gateway.exception.handler.CustomAccessDeniedHandler;
import com.gateway.exception.handler.CustomAuthenticationEntryPoint;
import com.gateway.exception.handler.CustomAuthenticationFailureHandler;
import com.gateway.filter.AuthenticationToHeaderFilter;
import com.gateway.filter.JwtExceptionHandlerFilter;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
		ReactiveAuthenticationManager authenticationManager,
		ServerAuthenticationConverter authenticationConverter,
		ServerSecurityContextRepository serverSecurityContextRepository) {
		AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
		authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);
		authenticationWebFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		authenticationWebFilter.setSecurityContextRepository(serverSecurityContextRepository);

		http
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
			.addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION) // JWT 인증 필터 추가
			.addFilterBefore(new JwtExceptionHandlerFilter(), SecurityWebFiltersOrder.AUTHENTICATION) // 예외 처리 필터 추가
			.addFilterAfter(new AuthenticationToHeaderFilter(serverSecurityContextRepository),
				SecurityWebFiltersOrder.AUTHENTICATION) // 사용자 정보 헤더 추가 필터 추가
			.authorizeExchange(exchange -> exchange
				.anyExchange().permitAll()) // 나머지 요청은 허용
			.exceptionHandling(exceptionHandling ->
				exceptionHandling
					.authenticationEntryPoint(authenticationEntryPoint)
					.accessDeniedHandler(accessDeniedHandler));

		return http.build();
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ServerSecurityContextRepository securityContextRepository() {
		return new WebSessionServerSecurityContextRepository();
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_SEMINAR_WRITER > ROLE_ACTIVE_USER > ROLE_USER");
		return roleHierarchyImpl;
	}

	@Bean
	public MethodSecurityExpressionHandler expressionHandler(RoleHierarchy roleHierarchy) {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		return expressionHandler;
	}
}
