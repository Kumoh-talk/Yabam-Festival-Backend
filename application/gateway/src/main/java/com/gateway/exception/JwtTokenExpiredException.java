package com.gateway.exception;

import com.exception.ErrorCode;

import lombok.Getter;

@Getter
public class JwtTokenExpiredException extends JwtAuthenticationException {

	public JwtTokenExpiredException(Throwable cause) {
		super(cause, ErrorCode.JWT_EXPIRED);
	}

}
