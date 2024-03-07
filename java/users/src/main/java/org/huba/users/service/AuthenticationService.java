package org.huba.users.service;

import lombok.RequiredArgsConstructor;

import org.huba.users.dto.token.RefreshTokenDto;
import org.huba.users.dto.user.CredentialsDto;
import org.huba.users.dto.user.RegisterDto;
import org.huba.users.dto.token.TokenDto;
import org.huba.users.exception.AuthException;
import org.huba.users.exception.BadLoginOrPasswordException;
import org.huba.users.exception.BadRequestException;
import org.huba.users.mapper.UserMapper;
import org.huba.users.model.TokenType;
import org.huba.users.model.User;
import org.huba.users.repository.UserRepository;
import org.huba.users.utils.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public TokenDto register(RegisterDto registerDto) {
        User user = userMapper.map(registerDto);

        if(userRepository.existsByPhone(registerDto.getPhone())) {
            throw new BadRequestException("phone already used");
        }

        //user.setPasswordHash(passwordEncoder.encode(registerDto.getPassword()));
        user.setPasswordHash(registerDto.getPassword());//для тестов

        user = userRepository.save(user);
        return new TokenDto(jwtProvider.generateAccessToken(user), tokenService.generateToken(user, TokenType.REFRESH));
    }

    public TokenDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByPhone(credentialsDto.getPhoneNumber()).orElseThrow(()->new AuthException("bad login or password"));

//        if(!passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
//            throw new BadLoginOrPasswordException();
//        }

        if(!credentialsDto.getPassword().equals(user.getPasswordHash())) {//для тестов
            throw new BadLoginOrPasswordException();
        }

        String accessToken = jwtProvider.generateAccessToken(user);
        String restoreToken = tokenService.generateToken(user, TokenType.REFRESH);
        return new TokenDto(restoreToken, accessToken);
    }

    public TokenDto refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        User user = tokenService.getByToken(refreshTokenDto.getRefresh(), TokenType.REFRESH, true);
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = tokenService.generateToken(user, TokenType.REFRESH);
        return new TokenDto(refreshToken, accessToken);
    }
}
