package org.huba.users.dto.token;

import lombok.Data;
import org.huba.users.dto.Dto;

@Data
public class RefreshTokenDto extends Dto {
    private String refresh;
}
