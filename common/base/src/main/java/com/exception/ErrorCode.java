package com.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	// Owner
	NOT_VALID_OWNER(HttpStatus.BAD_REQUEST, "OWNER_0001", "해당 사용자는 가게 점주가 아닙니다"),

	// Store
	NOT_EQUAL_STORE_OWNER(HttpStatus.CONFLICT, "STORE_0001", "해당 가게의 점주가 아닙니다"),
	NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "STORE_0002", "해당 가게를 찾을 수 없습니다"),

	// Auth
	INVALID_ID_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_0001", "해당 ID 토큰은 유효하지 않습니다."),
	ABNORMAL_ID_TOKEN(HttpStatus.BAD_REQUEST, "AUTH_0002", "해당 ID 토큰은 정상적이지 않습니다"),
	NOT_MATCHED_PUBLIC_KEY(HttpStatus.NOT_FOUND, "AUTH_0003", "해당 ID 토큰의 공개키를 찾을 수 없습니다");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
