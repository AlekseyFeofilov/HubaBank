package org.huba.users.service;

import org.huba.users.dto.CreatePrivilegeDto;
import org.huba.users.dto.EditUserPrivilegeDto;
import org.huba.users.dto.PrivilegeDto;
import org.huba.users.dto.ShortPrivilegeDto;
import org.huba.users.exception.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Service
public class PrivilegeService {
    public List<ShortPrivilegeDto> getUserPrivilege(@PathVariable UUID uuid) {
        throw new NotImplementedException();
    }

    public void editUserPrivilege(@PathVariable UUID uuid, @RequestBody EditUserPrivilegeDto editUserPrivilegeDto) {
        throw new NotImplementedException();
    }

    public List<PrivilegeDto> getAllPrivileges() {
        throw new NotImplementedException();
    }

    public void deletePrivilege(@PathVariable UUID uuid) {
        throw new NotImplementedException();
    }

    public void createPrivileges(CreatePrivilegeDto createPrivilegeDto) {
        throw new NotImplementedException();
    }
}
