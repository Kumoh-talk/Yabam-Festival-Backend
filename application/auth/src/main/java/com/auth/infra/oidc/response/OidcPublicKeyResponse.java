package com.auth.infra.oidc.response;

import java.util.List;

import com.auth.infra.oidc.dto.OidcPublicKey;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OidcPublicKeyResponse {
	List<OidcPublicKey> keys;
}
