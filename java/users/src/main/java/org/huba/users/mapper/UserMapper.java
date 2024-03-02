package org.huba.users.mapper;

import org.huba.users.dto.RegisterDto;
import org.huba.users.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "firstName", source = "fullName.firstName")
    @Mapping(target = "secondName", source = "fullName.secondName")
    @Mapping(target = "thirdName", source = "fullName.thirdName")
    @Mapping(target = "passwordHash", ignore = true) //не мапаем пароль в рамках политики безопастности.
    User map(RegisterDto registerDto);
}
