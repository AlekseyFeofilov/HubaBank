package org.huba.users.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EditUserPrivilegeDto {
    private List<String> privilegeIds;
}
