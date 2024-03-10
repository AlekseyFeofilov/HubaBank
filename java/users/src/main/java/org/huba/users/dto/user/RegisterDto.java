package org.huba.users.dto.user;

import lombok.Data;
import org.huba.users.dto.user.FullNameDto;

@Data
public class RegisterDto {
    private FullNameDto fullName;
    private String password;
    private String phone;
}
