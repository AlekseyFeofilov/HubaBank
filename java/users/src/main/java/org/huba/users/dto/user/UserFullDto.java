package org.huba.users.dto.user;

import lombok.Data;
import org.huba.users.dto.user.FullNameDto;

import java.util.List;

@Data
public class UserFullDto {
    private FullNameDto fullNameDto;
    private String phone;
    private boolean employee;
    private List<String> privileges;
    private List<String> roles;
}
