package org.huba.users.dto.user;

import lombok.Data;
import org.huba.users.dto.Dto;

@Data
public class CredentialsDto extends Dto {
    private String phoneNumber;
    private String password;
}
