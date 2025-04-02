package com.auth.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	@PostMapping("/login")
	public ResponseEntity<Void> login(HttpServletRequest request) {
		String userId = request.getHeader("X-User-Id");
		String authorities = request.getHeader("X-Authorities");
		System.out.println("userId = " + userId + ", authorities = " + authorities);
		return ResponseEntity.ok().build();
	}
}
