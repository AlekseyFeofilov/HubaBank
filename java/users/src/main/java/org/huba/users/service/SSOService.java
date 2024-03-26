package org.huba.users.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.huba.users.dto.token.TokenDto;
import org.huba.users.dto.user.CredentialsDto;
import org.huba.users.exception.AuthException;
import org.huba.users.exception.BadLoginOrPasswordException;
import org.huba.users.exception.ForbiddenException;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.mapper.UserMapper;
import org.huba.users.model.TokenType;
import org.huba.users.model.User;
import org.huba.users.repository.UserRepository;
import org.huba.users.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;

@Service
@RequiredArgsConstructor
public class SSOService {
    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    @Value( "${sso.loginUrl:http://localhost:8080/users/api/v1/SSO_login}" )
    private String loginUrl;

    @Value( "${sso.testPageUrl:http://localhost:8080/users/api/v1/test_page}" )
    private String testPageUrl;

    @Value( "${sso.loginPageUrl:http://localhost:8080/users/api/v1/auth_page}" )
    private String loginPageUrl;

    @Value( "${sso.getJWTURL:http://localhost:8080/users/api/v1/jwt}" )
    private String getJWTURL;
    private String authPage;
    private String testPage;
    public String getAuthPage(String redirectedUrl) {
        return authPage.replace("{redirectUrl}", redirectedUrl).replace("{serverUrl}", loginUrl);
    }

    public String getTestPage(String token) {
        return testPage.replace("{getJWTURL}", getJWTURL).replace("{sso-page}", loginPageUrl).replace("{testPageURL}", testPageUrl);
    }


    public String SSOLogin(CredentialsDto credentialsDto) {
        User user = authenticationService.getUserByCredentials(credentialsDto);
        return tokenService.generateToken(user, TokenType.AUTH_TOKEN);
    }


    public TokenDto token(String token) {
        User user = tokenService.getByToken(token, TokenType.AUTH_TOKEN, true);
        return authenticationService.getTokenByUser(user);
    }

    @SneakyThrows
    @PostConstruct
    public void init(){
        File file1 = ResourceUtils.getFile("classpath:authPage.html");
        authPage = FileUtils.readFileToString(file1, "utf-8");

        File file2 = ResourceUtils.getFile("classpath:testPage.html");
        testPage = FileUtils.readFileToString(file2, "utf-8");
    }
}
