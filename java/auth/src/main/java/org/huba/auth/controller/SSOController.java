package org.huba.auth.controller;

import lombok.RequiredArgsConstructor;
import org.huba.auth.dto.token.TokenDto;
import org.huba.auth.dto.user.CredentialsDto;
import org.huba.auth.service.SSOService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.huba.auth.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class SSOController {
    private final SSOService ssoService;
    @GetMapping(value = "auth_page")
    public ResponseEntity<String> getAuthPage(@RequestParam String redirectedUrl) {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/html;")
                .body(ssoService.getAuthPage(redirectedUrl));
    }

    @GetMapping(value = "test_page")
    public ResponseEntity<String> getTestPage(@RequestParam(required = false) String token) {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/html;")
                .body(ssoService.getTestPage(token));
    }

    @PostMapping(value = "SSO_login")
    public String SSOLogin(@RequestBody CredentialsDto credentialsDto) {
        return ssoService.SSOLogin(credentialsDto);
    }

    @PostMapping(value = "jwt")
    public TokenDto token(@RequestBody String token) {
        return ssoService.token(token);
    }
}
