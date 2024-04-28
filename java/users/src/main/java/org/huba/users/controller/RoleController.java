package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.role.EditUserRoleDto;
import org.huba.users.dto.role.RoleCreateDto;
import org.huba.users.dto.role.RoleDto;
import org.huba.users.dto.role.RoleEditDto;
import org.huba.users.service.ErrorService;
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
    private final ErrorService errorService;
    @GetMapping("roles")
    public List<RoleDto> getRoles(@RequestHeader(value = "requestId", required = false) String requestId) {
        return roleService.getRoles();
    }

    @PutMapping("role/{name}")
    public void editRole(@RequestBody RoleEditDto roleEditDto, @PathVariable String name, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        roleService.editRole(roleEditDto, name);
    }

    @PostMapping("role")
    public void createRole(@RequestBody RoleCreateDto roleCreateDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        roleService.createRole(roleCreateDto);
    }

    @DeleteMapping("role/{name}")
    public void deleteRole(@PathVariable String name, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        roleService.deleteRole(name);
    }

    @PostMapping("user/{uuid}/roles")
    public void editUserRoles(@RequestBody EditUserRoleDto editUserRoleDto, @PathVariable UUID uuid, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        roleService.editUserRoles(editUserRoleDto, uuid);
    }
}
