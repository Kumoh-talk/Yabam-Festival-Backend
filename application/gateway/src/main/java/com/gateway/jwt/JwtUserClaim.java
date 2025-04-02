package com.gateway.jwt;

import com.gateway.user.Role;

public record JwtUserClaim(
	Long userId,
	Role role
) {
	public static JwtUserClaim create(Long userId, Role role) {
		return new JwtUserClaim(userId, role);
	}
}
