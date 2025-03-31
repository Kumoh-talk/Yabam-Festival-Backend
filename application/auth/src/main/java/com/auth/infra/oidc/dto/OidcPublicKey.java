package com.auth.infra.oidc.dto;

public record OidcPublicKey(
	String kid,
	String kty,
	String alg,
	String use,
	String n,
	String e
) {
}
