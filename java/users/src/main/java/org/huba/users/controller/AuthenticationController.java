package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.CredentialsDto;
import org.huba.users.dto.RefreshTokenDto;
import org.huba.users.dto.RegisterDto;
import org.huba.users.dto.TokenDto;
import org.huba.users.exception.NotImplementedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class AuthenticationController {

    @PostMapping("register")
    public TokenDto register(RegisterDto registerDto) {
        throw new NotImplementedException();
    }

    @PostMapping("login")
    public TokenDto login(CredentialsDto credentialsDto) {
        throw new NotImplementedException();
    }

    @PostMapping("refresh")
    public TokenDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        throw new NotImplementedException();
    }
}
