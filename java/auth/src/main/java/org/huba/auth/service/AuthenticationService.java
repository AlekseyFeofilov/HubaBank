package org.huba.auth.service;

import lombok.RequiredArgsConstructor;
import org.huba.auth.dto.token.TokenDto;
import org.huba.auth.dto.user.CredentialsDto;
import org.huba.auth.dto.user.RegisterDto;
import org.huba.auth.exception.BadRequestException;
import org.huba.auth.model.User;
import org.huba.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public String register(RegisterDto registerDto) {
        User user = new User();

        if(userRepository.existsByPhone(registerDto.getPhone())) {
            throw new BadRequestException("phone already used");
        }

        //user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        user.setPasswordHash(registerDto.getPassword());//для тестов

        user = userRepository.save(user);
        return new TokenDto(jwtProvider.generateAccessToken(user), tokenService.generateToken(user, TokenType.REFRESH));
    }

}
