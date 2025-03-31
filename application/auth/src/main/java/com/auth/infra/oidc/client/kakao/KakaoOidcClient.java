package com.auth.infra.oidc.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth.infra.oidc.client.OidcClient;
import com.auth.infra.oidc.response.OidcPublicKeyResponse;

@FeignClient(
	name = "KakaoOidcClient",
	url = "${oauth2.client.provider.kakao.jwks-uri}",
	qualifiers = "kakaoOidcClient"
)
public interface KakaoOidcClient extends OidcClient {
	@Override
	@GetMapping("/.well-known/jwks.json")
	OidcPublicKeyResponse getOidcPublicKey();
}
