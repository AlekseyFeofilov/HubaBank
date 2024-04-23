package ru.hubabank.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hubabank.core.dto.TransferDto;
import ru.hubabank.core.entity.Transfer;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransferMapper {

    @Mapping(source = "source.id", target = "source.billId")
    @Mapping(source = "withdrawal", target = "source.amount")
    @Mapping(source = "target.id", target = "target.billId")
    @Mapping(source = "deposit", target = "target.amount")
    TransferDto mapEntityToDto(Transfer entity);
}
