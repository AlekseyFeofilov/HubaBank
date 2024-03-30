package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.user.CredentialsDto;
import org.huba.users.dto.user.FullNameDto;
import org.huba.users.dto.user.UserFullDto;
import org.huba.users.exception.*;
import org.huba.users.model.Privilege;
import org.huba.users.model.Role;
import org.huba.users.model.User;
import org.huba.users.repository.UserRepository;
import org.huba.users.utils.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    public List<UserFullDto> getUsers() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(!myUser.isEmployee()) {
            throw new ForbiddenException();
        }

        return userRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    public UserFullDto getMyInfo() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);
        return map(myUser);
    }

    public List<UserFullDto> getEmployees() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(myUser.isEmployee()) {
            throw new ForbiddenException();
        }

        return userRepository.findByEmployee(true).stream().map(this::map).collect(Collectors.toList());
    }

    public UserFullDto getUserByUUID(UUID uuid) {
        return map(userRepository.findById(uuid).orElseThrow(NotFoundException::new));
    }

    public void block(@PathVariable UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(NotFoundException::new);
        user.setBlockedDate(new Date(System.currentTimeMillis()));
        userRepository.save(user);
    }

    public void unblock(@PathVariable UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(NotFoundException::new);
        user.setBlockedDate(null);
        userRepository.save(user);
    }

    private UserFullDto map(User user) {
        UserFullDto dto = new UserFullDto();
        dto.setId(user.getId());
        dto.setFullNameDto(new FullNameDto(user.getFirstName(), user.getSecondName(), user.getThirdName()));
        dto.setPrivileges(user.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        dto.setAdditionPrivileges(user.getAdditionPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        dto.setBlockedPrivileges(user.getBlockedPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        dto.setPhone(user.getPhone());
        dto.setEmployee(user.isEmployee());
        dto.setBlocked(user.getBlockedDate()!=null);
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return dto;
    }
}
