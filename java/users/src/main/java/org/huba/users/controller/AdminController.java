package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.exception.BadRequestException;
import org.huba.users.exception.NotFoundException;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.logger.Loggable;
import org.huba.users.model.Privilege;
import org.huba.users.model.User;
import org.huba.users.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Loggable
    @GetMapping(value = "getAllUserPage")
    public ResponseEntity<String> getAllUserPage() {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/html;")
                .body(adminService.getAllUserPage());
    }

    @Loggable
    @GetMapping(value = "getAllPrivilegesPage")
    public ResponseEntity<String> getAllPrivilegesPage() {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/html;")
                .body(adminService.getAllPrivilegesPage());
    }

    @Loggable
    @GetMapping(value = "getAllRolesPage")
    public ResponseEntity<String> getAllRolesPage() {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "text/html;")
                .body(adminService.getAllRolesPage());
    }

    @Loggable
    @PostMapping(value = "createAdminAndEmployeePrivileges")
    public void createAdminAndEmployeePrivileges() {
        adminService.createAdminAndEmployeePrivileges();
    }

    @Loggable
    @PostMapping(value = "setAdminUser/{userId}")
    public void setAdminUser(@PathVariable UUID userId) {
        adminService.setAdminUser(userId);
    }

    @Loggable
    @PostMapping(value = "setEmployeeUser/{userId}")
    public void setEmployeeUser(@PathVariable UUID userId) {
        adminService.setEmployeeUser(userId);
    }
}
