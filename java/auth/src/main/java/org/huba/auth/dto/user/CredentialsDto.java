package org.huba.auth.dto.user;

import lombok.Data;

@Data
public class CredentialsDto {
    private String phoneNumber;
    private String password;
}
