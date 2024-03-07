package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.role.EditUserRoleDto;
import org.huba.users.dto.role.RoleCreateDto;
import org.huba.users.dto.role.RoleDto;
import org.huba.users.dto.role.RoleEditDto;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
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
