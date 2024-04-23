package org.huba.users.dto.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.huba.users.dto.Dto;

@Data
@AllArgsConstructor
public class TokenDto extends Dto {
    private String accessToken;
    private String refreshToken;
}
