package com.auth.infra.oidc.client;

import com.auth.infra.oidc.response.OidcPublicKeyResponse;

public interface OidcClient {
	OidcPublicKeyResponse getOidcPublicKey();
}
