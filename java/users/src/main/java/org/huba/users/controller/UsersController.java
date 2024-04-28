package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.user.UserFullDto;
import org.huba.users.service.ErrorService;
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
    private final ErrorService errorService;
    @GetMapping("users/my")
    public UserFullDto getMyInfo(@RequestHeader(value = "requestId", required = false) String requestId) {
        return usersService.getMyInfo();
    }

    @GetMapping("employees")
    public List<UserFullDto> getEmployees(@RequestHeader(value = "requestId", required = false) String requestId) {
        return usersService.getEmployees();
    }

    @GetMapping("users")
    public List<UserFullDto> getUsers(@RequestHeader(value = "requestId", required = false) String requestId) {
        return usersService.getUsers();
    }

    @GetMapping("user/{uuid}")
    public UserFullDto getUserByUUID(@PathVariable UUID uuid, @RequestHeader(value = "requestId", required = false) String requestId) {
        return usersService.getUserByUUID(uuid);
    }

    @PostMapping("user/{uuid}/block")
    public void block(@PathVariable UUID uuid, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        usersService.block(uuid);
    }

    @PostMapping("user/{uuid}/unblock")
    public void unblock(@PathVariable UUID uuid, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        usersService.unblock(uuid);
    }
}
