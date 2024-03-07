package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.privilege.CreatePrivilegeDto;
import org.huba.users.dto.user.EditUserPrivilegeDto;
import org.huba.users.dto.privilege.PrivilegeDto;
import org.huba.users.dto.privilege.ShortPrivilegeDto;
import org.huba.users.exception.BadRequestException;
import org.huba.users.exception.ForbiddenException;
import org.huba.users.exception.NotFoundException;
import org.huba.users.model.Privilege;
import org.huba.users.model.User;
import org.huba.users.repository.PrivilegesRepository;
import org.huba.users.repository.UserRepository;
import org.huba.users.utils.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivilegeService {
    private final UserRepository userRepository;
    private final PrivilegesRepository privilegesRepository;
    private final JwtProvider jwtProvider;
    public List<ShortPrivilegeDto> getUserPrivilege(@PathVariable UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(NotFoundException::new);
        return user.getPrivileges().stream().map(privilege -> {
            ShortPrivilegeDto dto = new ShortPrivilegeDto();
            dto.setName(privilege.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public void editUserBlockedPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(!myUser.isEmployee()) {
            throw new ForbiddenException();
        }

        User user = userRepository.findById(uuid).orElseThrow(NotFoundException::new);
        user.getBlockedPrivileges().clear();
        for(Privilege privilege : privilegesRepository.findAllById(editUserPrivilegeDto.getPrivileges())) {
            user.getBlockedPrivileges().add(privilege);
        }
        userRepository.save(user);
    }

    public void editUserAdditionPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(!myUser.isEmployee()) {
            throw new ForbiddenException();
        }

        User user = userRepository.findById(uuid).orElseThrow(NotFoundException::new);
        user.getAdditionPrivileges().clear();
        for(Privilege privilege : privilegesRepository.findAllById(editUserPrivilegeDto.getPrivileges())) {
            user.getAdditionPrivileges().add(privilege);
        }
        userRepository.save(user);
    }

    public List<PrivilegeDto> getAllPrivileges() {
        return privilegesRepository.findAll().stream().map(privilege -> {
            PrivilegeDto dto = new PrivilegeDto();
            dto.setName(privilege.getName());
            dto.setDescription(privilege.getDescription());
            return dto;
        }).collect(Collectors.toList());
    }

    public void deletePrivilege(@PathVariable String name) {
        Privilege privilege = privilegesRepository.findById(name).orElseThrow(NotFoundException::new);
        privilegesRepository.delete(privilege);
    }

    public void createPrivileges(CreatePrivilegeDto createPrivilegeDto) {
        Privilege privilege = privilegesRepository.findById(createPrivilegeDto.getDescription()).orElse(null);

        if(privilege != null) {
            throw new BadRequestException();
        }

        privilege = new Privilege();
        privilege.setName(createPrivilegeDto.getName());
        privilege.setDescription(createPrivilegeDto.getDescription());
        privilegesRepository.save(privilege);
    }
}
