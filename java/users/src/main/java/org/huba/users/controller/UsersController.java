package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.user.UserFullDto;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.logger.Loggable;
import org.huba.users.service.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @Loggable
    @GetMapping("users/my")
    public UserFullDto getMyInfo() {
        return usersService.getMyInfo();
    }

    @Loggable
    @GetMapping("employees")
    public List<UserFullDto> getEmployees() {
        return usersService.getEmployees();
    }

    @Loggable
    @GetMapping("users")
    public List<UserFullDto> getUsers() {
        return usersService.getUsers();
    }

    @Loggable
    @GetMapping("user/{uuid}")
    public UserFullDto getUserByUUID(@PathVariable UUID uuid) {
        return usersService.getUserByUUID(uuid);
    }

    @Loggable
    @PostMapping("user/{uuid}/block")
    public void block(@PathVariable UUID uuid) {
        usersService.block(uuid);
    }

    @Loggable
    @PostMapping("user/{uuid}/unblock")
    public void unblock(@PathVariable UUID uuid) {
        usersService.unblock(uuid);
    }
}
