package org.huba.auth.dto.user;

import lombok.Data;

@Data
public class RegisterDto {
    private FullNameDto fullName;
    private String password;
    private String phone;
}
