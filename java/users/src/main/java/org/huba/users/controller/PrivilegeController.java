package org.huba.users.controller;

import lombok.RequiredArgsConstructor;
import org.huba.users.dto.privilege.CreatePrivilegeDto;
import org.huba.users.dto.user.EditUserPrivilegeDto;
import org.huba.users.dto.privilege.PrivilegeDto;
import org.huba.users.dto.privilege.ShortPrivilegeDto;
import org.huba.users.service.ErrorService;
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
    private final ErrorService errorService;
    @GetMapping("user/{uuid}/privileges")
    public List<ShortPrivilegeDto> getUserPrivilege(@PathVariable UUID uuid, @RequestHeader(value = "requestId", required = false) String requestId) {
        return privilegeService.getUserPrivilege(uuid);
    }

    @PostMapping("user/{uuid}/privileges/addition")
    public void editUserAdditionPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        privilegeService.editUserAdditionPrivilege(uuid, editUserPrivilegeDto);
    }

    @PostMapping("user/{uuid}/privileges/blocked")
    public void editUserBlockedPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        privilegeService.editUserBlockedPrivilege(uuid, editUserPrivilegeDto);
    }

    @GetMapping("privileges")
    public List<PrivilegeDto> getAllPrivileges(@RequestHeader(value = "requestId", required = false) String requestId) {
        return privilegeService.getAllPrivileges();
    }

    @DeleteMapping("privileges/{name}")
    public void deletePrivilege(@PathVariable String name, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        privilegeService.deletePrivilege(name);
    }

    @PostMapping("privileges")
    public void createPrivileges(CreatePrivilegeDto createPrivilegeDto, @RequestHeader(value = "requestId", required = false) String requestId, @RequestHeader(value = "idempotentKey", required = false) String idempotentKey) {
        privilegeService.createPrivileges(createPrivilegeDto);
    }
}
