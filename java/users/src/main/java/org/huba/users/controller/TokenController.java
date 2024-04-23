package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.logger.Loggable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class TokenController {
    @Loggable
    @PostMapping("token/check")
    public boolean checkToken() {
        throw new NotImplementedException();
    }
}
