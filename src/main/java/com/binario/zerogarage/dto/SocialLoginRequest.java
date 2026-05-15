package com.binario.zerogarage.dto;

public record SocialLoginRequest(
        String provider,
        String providerId,
        String email
) {
}
