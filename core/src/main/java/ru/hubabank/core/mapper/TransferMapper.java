package ru.hubabank.core.mapper;

import org.mapstruct.Mapper;
import ru.hubabank.core.dto.TransferDto;
import ru.hubabank.core.entity.Transfer;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransferMapper {

    TransferDto mapEntityToDto(Transfer entity);
}
