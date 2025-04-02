package com.gateway.exception;

import org.springframework.security.core.AuthenticationException;

import com.exception.ErrorCode;

import lombok.Getter;

@Getter
public class JwtAuthenticationException extends AuthenticationException {
	private ErrorCode errorCode;

	public JwtAuthenticationException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public JwtAuthenticationException(Throwable cause, ErrorCode errorCode) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}
}

