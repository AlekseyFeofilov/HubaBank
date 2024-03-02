package org.huba.users.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PrivilegeDto {
    private String description;
    private boolean admin;
    private String name;
}
