package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.service.ErrorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class TokenController {
    private final ErrorService errorService;
    @PostMapping("token/check")
    public boolean checkToken(@RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        throw new NotImplementedException();
    }
}
