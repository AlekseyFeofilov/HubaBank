package org.huba.users.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class EditUserPrivilegeDto {
    private List<String> privileges;
}
