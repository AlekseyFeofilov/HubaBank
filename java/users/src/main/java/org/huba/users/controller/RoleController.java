package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.role.EditUserRoleDto;
import org.huba.users.dto.role.RoleCreateDto;
import org.huba.users.dto.role.RoleDto;
import org.huba.users.dto.role.RoleEditDto;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.logger.Loggable;
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

    @Loggable
    @GetMapping("roles")
    public List<RoleDto> getRoles() {
        return roleService.getRoles();
    }

    @Loggable
    @PutMapping("role/{name}")
    public void editRole(@RequestBody RoleEditDto roleEditDto, @PathVariable String name) {
        roleService.editRole(roleEditDto, name);
    }

    @Loggable
    @PostMapping("role")
    public void createRole(@RequestBody RoleCreateDto roleCreateDto) {
       roleService.createRole(roleCreateDto);
    }

    @Loggable
    @DeleteMapping("role/{name}")
    public void deleteRole(@PathVariable String name) {
        roleService.deleteRole(name);
    }

    @Loggable
    @PostMapping("user/{uuid}/roles")
    public void editUserRoles(@RequestBody EditUserRoleDto editUserRoleDto, @PathVariable UUID uuid) {
        roleService.editUserRoles(editUserRoleDto, uuid);
    }
}
