package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.CredentialsDto;
import org.huba.users.dto.RegisterDto;
import org.huba.users.dto.TokenDto;
import org.huba.users.exception.NotImplementedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @PostMapping("users/api/v1/register")
    public TokenDto register(RegisterDto registerDto) {
        throw new NotImplementedException();
    }

    @PostMapping("users/api/v1/login")
    public TokenDto login(CredentialsDto credentialsDto) {
        throw new NotImplementedException();
    }
}
