package com.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// TODO : 곧 삭제 될 예정
	TEST_ERROR(HttpStatus.BAD_GATEWAY, "TEST_0001", "멀티 모듈 환경에서 에러 핸들링 테스트");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
