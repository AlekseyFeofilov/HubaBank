package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.UserFullDto;
import org.huba.users.repository.UserRepository;
import org.huba.users.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    @GetMapping("users")
    public List<UserFullDto> getUsers() {
        return usersService.getUsers();
    }
}
