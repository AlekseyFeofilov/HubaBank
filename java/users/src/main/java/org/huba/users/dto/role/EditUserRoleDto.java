package org.huba.users.dto.role;

import lombok.Data;
import org.huba.users.dto.Dto;

import java.util.List;

@Data
public class EditUserRoleDto extends Dto {
    private List<String> names;
}
