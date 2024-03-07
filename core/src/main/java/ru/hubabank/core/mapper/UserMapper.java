package ru.hubabank.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hubabank.core.integration.dto.UserFullDto;
import ru.hubabank.core.integration.object.UserInfo;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    @Mapping(source = "uuid", target = "id")
    UserInfo mapDtoToObject(UserFullDto dto);
}
