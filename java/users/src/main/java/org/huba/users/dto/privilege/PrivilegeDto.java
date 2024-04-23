package org.huba.users.dto.privilege;

import lombok.Data;
import org.huba.users.dto.Dto;

import java.util.UUID;

@Data
public class PrivilegeDto extends Dto {
    private String description;
    private String name;
}
