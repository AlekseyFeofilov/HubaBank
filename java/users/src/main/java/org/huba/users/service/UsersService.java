package org.huba.users.service;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.FullNameDto;
import org.huba.users.dto.UserFullDto;
import org.huba.users.exception.ForbiddenException;
import org.huba.users.exception.NotFoundException;
import org.huba.users.model.Privilege;
import org.huba.users.model.User;
import org.huba.users.repository.UserRepository;
import org.huba.users.utils.JwtProvider;
import org.huba.users.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    public List<UserFullDto> getUsers() {
        User myUser = userRepository.findById(jwtProvider.getId()).orElseThrow(NotFoundException::new);

        if(!Utils.checkAdmin(myUser) && !Utils.checkRole(myUser, "EMPLOYEE")) {
            throw new ForbiddenException();
        }

        return userRepository.findAll().stream().map(user -> {
            UserFullDto dto = new UserFullDto();
            dto.setFullNameDto(new FullNameDto(user.getFirstName(), user.getSecondName(), user.getThirdName()));
            dto.setPrivileges(user.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toList()));
            dto.setPhone(user.getPhone());
            return dto;
        }).collect(Collectors.toList());
    }
}
