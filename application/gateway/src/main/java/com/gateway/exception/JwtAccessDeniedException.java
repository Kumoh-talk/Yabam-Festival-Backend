package com.gateway.exception;

import com.exception.ErrorCode;

public class JwtAccessDeniedException extends JwtAuthenticationException {
	public JwtAccessDeniedException() {
		super(ErrorCode.ACCESS_DENIED);
	}
}
