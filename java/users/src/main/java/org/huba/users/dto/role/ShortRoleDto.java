package org.huba.users.dto.role;

import lombok.Data;
import org.huba.users.dto.Dto;

@Data
public class ShortRoleDto extends Dto {
    private String name;
    private String description;
}
