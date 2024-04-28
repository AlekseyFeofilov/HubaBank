package org.huba.users.controller;

import lombok.RequiredArgsConstructor;

import org.huba.users.dto.token.RefreshTokenDto;
import org.huba.users.dto.user.CredentialsDto;
import org.huba.users.dto.user.RegisterDto;
import org.huba.users.dto.token.TokenDto;
import org.huba.users.service.AuthenticationService;
import org.huba.users.service.ErrorService;
import org.springframework.web.bind.annotation.*;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final ErrorService errorService;
    @PostMapping("register")
    public TokenDto register(@RequestBody RegisterDto registerDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        return authenticationService.register(registerDto);
    }

    @PostMapping("login")
    public TokenDto login(@RequestBody CredentialsDto credentialsDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        return authenticationService.login(credentialsDto);
    }

    @PostMapping("refresh")
    public TokenDto refresh(@RequestBody RefreshTokenDto refreshTokenDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        return authenticationService.refresh(refreshTokenDto);
    }
}
