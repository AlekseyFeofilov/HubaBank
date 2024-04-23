package org.huba.users.dto.user;

import lombok.Data;
import org.huba.users.dto.Dto;

import java.util.List;
import java.util.UUID;

@Data
public class EditUserPrivilegeDto extends Dto {
    private List<String> privileges;
}
