package org.huba.users.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserFullDto {
    private FullNameDto fullNameDto;
    private String phone;
    private List<String> privileges;
}
