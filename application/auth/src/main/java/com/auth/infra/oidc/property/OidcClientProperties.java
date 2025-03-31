package com.auth.infra.oidc.property;

public interface OidcClientProperties {
	String getJwksUri();

	String getSecret();

	String getIssuer();
}
