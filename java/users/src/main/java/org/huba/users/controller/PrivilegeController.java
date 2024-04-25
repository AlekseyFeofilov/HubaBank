package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.privilege.CreatePrivilegeDto;
import org.huba.users.dto.user.EditUserPrivilegeDto;
import org.huba.users.dto.privilege.PrivilegeDto;
import org.huba.users.dto.privilege.ShortPrivilegeDto;
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

    @PostMapping("user/{uuid}/privileges/addition")
    public void editUserAdditionPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        privilegeService.editUserAdditionPrivilege(uuid, editUserPrivilegeDto);
    }

    @PostMapping("user/{uuid}/privileges/blocked")
    public void editUserBlockedPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        privilegeService.editUserBlockedPrivilege(uuid, editUserPrivilegeDto);
    }

    @GetMapping("privileges")
    public List<PrivilegeDto> getAllPrivileges() {
        return privilegeService.getAllPrivileges();
    }

    @DeleteMapping("privileges/{name}")
    public void deletePrivilege(@PathVariable String name) {
        privilegeService.deletePrivilege(name);
    }

    @PostMapping("privileges")
    public void createPrivileges(CreatePrivilegeDto createPrivilegeDto) {
        privilegeService.createPrivileges(createPrivilegeDto);
    }
}
