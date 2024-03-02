package org.huba.users.service;

import org.huba.users.dto.CredentialsDto;
import org.huba.users.dto.RefreshTokenDto;
import org.huba.users.dto.RegisterDto;
import org.huba.users.dto.TokenDto;
import org.huba.users.exception.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {
    public TokenDto register(RegisterDto registerDto) {
        throw new NotImplementedException();
    }

    public TokenDto login(CredentialsDto credentialsDto) {
        throw new NotImplementedException();
    }

    public TokenDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        throw new NotImplementedException();
    }
}
