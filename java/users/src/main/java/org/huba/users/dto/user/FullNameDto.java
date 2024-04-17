package org.huba.users.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.huba.users.dto.Dto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullNameDto extends Dto {
    private String firstName;
    private String secondName;
    private String thirdName;
}
