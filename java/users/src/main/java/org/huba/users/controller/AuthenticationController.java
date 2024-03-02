package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.CredentialsDto;
import org.huba.users.dto.RefreshTokenDto;
import org.huba.users.dto.RegisterDto;
import org.huba.users.dto.TokenDto;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public TokenDto register(@RequestBody RegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }

    @PostMapping("login")
    public TokenDto login(@RequestBody CredentialsDto credentialsDto) {
        return authenticationService.login(credentialsDto);
    }

    @PostMapping("refresh")
    public TokenDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        return authenticationService.refresh(refreshTokenDto);
    }
}
