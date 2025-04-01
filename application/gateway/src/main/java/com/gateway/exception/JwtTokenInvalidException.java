package com.gateway.exception;

import com.exception.ErrorCode;

public class JwtTokenInvalidException extends JwtAuthenticationException {
	public JwtTokenInvalidException() {
		super(ErrorCode.JWT_INVALID);
	}

	public JwtTokenInvalidException(Throwable cause) {
		super(cause, ErrorCode.JWT_INVALID);
	}
}
