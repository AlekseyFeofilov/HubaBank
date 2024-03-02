package org.huba.users.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private FullNameDto fullName;
    private String password;
    private String phoneNumber;
}
