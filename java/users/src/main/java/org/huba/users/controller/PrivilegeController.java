package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.CreatePrivilegeDto;
import org.huba.users.dto.EditUserPrivilegeDto;
import org.huba.users.dto.PrivilegeDto;
import org.huba.users.dto.ShortPrivilegeDto;
import org.huba.users.exception.NotImplementedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class PrivilegeController {

    @GetMapping("user/{uuid}/privileges")
    public List<ShortPrivilegeDto> getUserPrivilege(@PathVariable UUID uuid) {
        throw new NotImplementedException();
    }

    @PostMapping("user/{uuid}/privileges")
    public void editUserPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        throw new NotImplementedException();
    }

    @GetMapping("privileges")
    public List<PrivilegeDto> getAllPrivileges() {
        throw new NotImplementedException();
    }

    @DeleteMapping("privileges/{uuid}")
    public void deletePrivilege(@PathVariable UUID uuid) {
        throw new NotImplementedException();
    }

    @PostMapping("privileges")
    public void createPrivileges(CreatePrivilegeDto createPrivilegeDto) {
        throw new NotImplementedException();
    }
}
