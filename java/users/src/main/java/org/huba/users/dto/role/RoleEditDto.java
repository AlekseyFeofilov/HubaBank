package org.huba.users.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RoleEditDto {
    private String name;
    private String description;
    private List<String> privileges;
}
