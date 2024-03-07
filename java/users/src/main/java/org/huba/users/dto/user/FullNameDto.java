package org.huba.users.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullNameDto {
    private String firstName;
    private String secondName;
    private String thirdName;
}
