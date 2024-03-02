package org.huba.users.dto;

import lombok.Data;

@Data
public class CreatePrivilegeDto {
    private String description;
    private boolean admin;
    private String name;
}
