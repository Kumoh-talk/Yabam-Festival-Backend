package com.gateway.jwt;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.gateway.user.Role;

public record JwtAuthentication(
	Long userId,
	String userNickname,
	Role userRole
) implements Authentication {

	public JwtAuthentication(JwtUserClaim claims) {
		this(
			claims.userId(),
			claims.userNickname(),
			claims.userRole()
		);
	}

	public static JwtAuthentication anonymousAuthentication() {
		return new JwtAuthentication(null, "Anonymous", Role.ROLE_ANONYMOUS);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(this.userRole().name()));
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return null;
	}
}
