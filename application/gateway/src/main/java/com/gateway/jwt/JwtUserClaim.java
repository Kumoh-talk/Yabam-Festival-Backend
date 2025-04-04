package com.gateway.jwt;

import com.gateway.user.Role;

public record JwtUserClaim(
	Long userId,
	String userNickname,
	Role userRole
) {
	public static JwtUserClaim create(Long userId, String userNickname, Role userRole) {
		return new JwtUserClaim(userId, userNickname, userRole);
	}
}
