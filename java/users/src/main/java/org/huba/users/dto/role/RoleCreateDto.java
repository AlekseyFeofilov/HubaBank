package org.huba.users.dto.role;

import lombok.Data;
import org.huba.users.dto.Dto;

import java.util.List;

@Data
public class RoleCreateDto extends Dto {
    private String name;
    private String description;
    private List<String> privileges;
}
