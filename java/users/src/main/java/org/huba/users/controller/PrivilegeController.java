package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.CreatePrivilegeDto;
import org.huba.users.dto.EditUserPrivilegeDto;
import org.huba.users.dto.PrivilegeDto;
import org.huba.users.dto.ShortPrivilegeDto;
import org.huba.users.exception.NotImplementedException;
import org.huba.users.service.PrivilegeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.huba.users.utils.MyConstants.USERS_SPI_URL;

@RestController
@RequestMapping(USERS_SPI_URL)
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    @GetMapping("user/{uuid}/privileges")
    public List<ShortPrivilegeDto> getUserPrivilege(@PathVariable UUID uuid) {
        return privilegeService.getUserPrivilege(uuid);
    }

    @PostMapping("user/{uuid}/privileges")
    public void editUserPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        privilegeService.editUserPrivilege(uuid, editUserPrivilegeDto);
    }

    @GetMapping("privileges")
    public List<PrivilegeDto> getAllPrivileges() {
        return privilegeService.getAllPrivileges();
    }

    @DeleteMapping("privileges/{uuid}")
    public void deletePrivilege(@PathVariable UUID uuid) {
        privilegeService.deletePrivilege(uuid);
    }

    @PostMapping("privileges")
    public void createPrivileges(CreatePrivilegeDto createPrivilegeDto) {
        privilegeService.createPrivileges(createPrivilegeDto);
    }
}
