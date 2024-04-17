package org.huba.users.dto.user;

import lombok.Data;
import org.huba.users.dto.Dto;
import org.huba.users.dto.user.FullNameDto;

@Data
public class RegisterDto extends Dto {
    private FullNameDto fullName;
    private String password;
    private String phone;
}
