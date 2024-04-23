package org.huba.users.dto.privilege;

import lombok.Data;
import org.huba.users.dto.Dto;

@Data
public class CreatePrivilegeDto extends Dto {
    private String description;
    private String name;
}
