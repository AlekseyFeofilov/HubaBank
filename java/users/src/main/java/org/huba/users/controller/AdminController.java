package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("get_all_user")
    public String getAllUserPage() {
        return adminService.getAllUserPage();
    }
}
