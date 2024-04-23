package org.huba.auth.service;

import org.huba.auth.dto.token.TokenDto;

import java.util.UUID;

public interface TokenService {
    TokenDto getToken(UUID userId);
}
