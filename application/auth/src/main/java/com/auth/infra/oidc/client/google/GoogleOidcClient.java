package com.auth.infra.oidc.client.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth.infra.oidc.client.OidcClient;
import com.auth.infra.oidc.response.OidcPublicKeyResponse;

@FeignClient(
	name = "GoogleOidcClient",
	url = "${oauth2.client.provider.google.jwks-uri}",
	qualifiers = "googleOidcClient"
)
public interface GoogleOidcClient extends OidcClient {
	@Override
	@GetMapping("/oauth2/v3/certs")
	OidcPublicKeyResponse getOidcPublicKey();
}
