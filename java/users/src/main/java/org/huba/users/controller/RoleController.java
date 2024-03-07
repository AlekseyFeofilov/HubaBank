package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.role.EditUserRoleDto;
import org.huba.users.dto.role.RoleCreateDto;
import org.huba.users.dto.role.RoleDto;
import org.huba.users.dto.role.RoleEditDto;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping("roles")
    public List<RoleDto> getRoles() {
        throw new NotImplementedException();
    }

    @PutMapping("role/{name}")
    public void editRole(@RequestBody RoleEditDto roleEditDto, @PathVariable String name) {
        throw new NotImplementedException();
    }

    @PostMapping("role")
    public void createRole(@RequestBody RoleCreateDto roleCreateDto) {
        throw new NotImplementedException();
    }

    @DeleteMapping("role/{name}")
    public void deleteRole(@PathVariable String name) {
        throw new NotImplementedException();
    }

    @PostMapping("user/{uuid}/roles")
    public void editUserRoles(@RequestBody EditUserRoleDto editUserRoleDto, @PathVariable UUID uuid) {
        throw new NotImplementedException();
    }
}
