package org.huba.auth.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.huba.users.dto.token.TokenDto;
import org.huba.users.dto.user.CredentialsDto;
import org.huba.users.model.TokenType;
import org.huba.users.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
        InputStream file1 = new ClassPathResource("authPage.html").getInputStream();
        authPage = IOUtils.toString(file1, StandardCharsets.UTF_8);

        InputStream file2 = new ClassPathResource("testPage.html").getInputStream();
        testPage = IOUtils.toString(file2, StandardCharsets.UTF_8);
    }
}
