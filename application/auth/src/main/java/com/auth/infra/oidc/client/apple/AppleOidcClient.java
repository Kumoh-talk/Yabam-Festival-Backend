package com.auth.infra.oidc.client.apple;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth.infra.oidc.client.OidcClient;
import com.auth.infra.oidc.response.OidcPublicKeyResponse;

@FeignClient(
	name = "AppleOidcClient",
	url = "${oauth2.client.provider.apple.jwks-uri}",
	qualifiers = "appleOidcClient"
)
public interface AppleOidcClient extends OidcClient {
	@Override
	@GetMapping("/auth/keys")
	OidcPublicKeyResponse getOidcPublicKey();
}
