package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.user.FullNameDto;
import org.huba.users.dto.user.UserFullDto;
import org.huba.users.exception.ForbiddenException;
import org.huba.users.exception.NotFoundException;
import org.huba.users.model.Privilege;
import org.huba.users.model.Role;
import org.huba.users.model.User;
import org.huba.users.repository.UserRepository;
import org.huba.users.utils.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    public List<UserFullDto> getUsers() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(myUser.isEmployee()) {
            throw new ForbiddenException();
        }

        return userRepository.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @GetMapping("users/my")
    public UserFullDto getMyInfo() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);
        return map(myUser);
    }

    @GetMapping("employees")
    public List<UserFullDto> getEmployees() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(myUser.isEmployee()) {
            throw new ForbiddenException();
        }

        return userRepository.findByEmployee(true).stream().map(this::map).collect(Collectors.toList());
    }

    private UserFullDto map(User user) {
        UserFullDto dto = new UserFullDto();
        dto.setFullNameDto(new FullNameDto(user.getFirstName(), user.getSecondName(), user.getThirdName()));
        dto.setPrivileges(user.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
        dto.setPhone(user.getPhone());
        dto.setEmployee(user.isEmployee());
        dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return dto;
    }
}
