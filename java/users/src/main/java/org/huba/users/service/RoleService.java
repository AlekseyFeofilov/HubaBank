package org.huba.users.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.huba.users.dto.role.EditUserRoleDto;
import org.huba.users.dto.role.RoleCreateDto;
import org.huba.users.dto.role.RoleDto;
import org.huba.users.dto.role.RoleEditDto;
import org.huba.users.exception.NotFoundException;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.model.Privilege;
import org.huba.users.model.Role;
import org.huba.users.model.User;
import org.huba.users.repository.PrivilegesRepository;
import org.huba.users.repository.RoleRepository;
import org.huba.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PrivilegesRepository privilegesRepository;
    private final UserRepository userRepository;
    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Transactional
    public void editRole(RoleEditDto roleEditDto, String name) {
        Role role = new Role();
        role.setDescription(roleEditDto.getDescription());
        role.setName(roleEditDto.getName());
        role.setPrivileges(roleEditDto.getPrivileges().stream().map(
                s -> privilegesRepository.findById(s).orElseThrow(NotFoundException::new)
        ).collect(Collectors.toSet()));
        Role oldRole = roleRepository.findById(name).orElseThrow(NotFoundException::new);
        roleRepository.delete(oldRole);
        roleRepository.save(role);
    }

    public void createRole(RoleCreateDto roleCreateDto) {
        Role role = new Role();
        role.setDescription(roleCreateDto.getDescription());
        role.setName(roleCreateDto.getName());
        role.setPrivileges(roleCreateDto.getPrivileges().stream().map(
                s -> privilegesRepository.findById(s).orElseThrow(NotFoundException::new)
        ).collect(Collectors.toSet()));
        roleRepository.save(role);
    }

    public void deleteRole(String name) {
        Role oldRole = roleRepository.findById(name).orElseThrow(NotFoundException::new);
        roleRepository.delete(oldRole);
    }

    public void editUserRoles(EditUserRoleDto editUserRoleDto, UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(NotFoundException::new);
        user.getRoles().clear();
        for(String name : editUserRoleDto.getNames()) {
            Role role = roleRepository.findById(name).orElseThrow(NotFoundException::new);
            user.getRoles().add(role);
        }
        userRepository.save(user);
    }

    private RoleDto map(Role role) {
        RoleDto dto = new RoleDto();
        dto.setDescription(role.getDescription());
        dto.setName(role.getName());
        dto.setPrivileges(role.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        return dto;
    }
}
