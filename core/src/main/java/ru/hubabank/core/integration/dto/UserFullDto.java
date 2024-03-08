package ru.hubabank.core.integration.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserFullDto {

    private UUID uuid;
    private FullNameDto fullNameDto;
    private String phone;
    private boolean employee;
    private boolean blocked;
    private List<String> privileges;
    private List<String> additionPrivileges;
    private List<String> blockedPrivileges;
    private List<String> roles;
}