package ru.hubabank.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hubabank.core.dto.TransactionCreationDto;
import ru.hubabank.core.dto.TransactionDto;
import ru.hubabank.core.entity.Transaction;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TransactionMapper {

    @Mapping(source = "bill.id", target = "billId")
    TransactionDto mapEntityToDto(Transaction entity);

    Transaction mapCreationDtoToEntity(TransactionCreationDto creationDto);
}
