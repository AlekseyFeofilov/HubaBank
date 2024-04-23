package org.huba.users.dto.token;

import lombok.Data;
import org.huba.users.dto.Dto;

@Data
public class AccessTokenDto extends Dto {
    private final String accessToken;
}
