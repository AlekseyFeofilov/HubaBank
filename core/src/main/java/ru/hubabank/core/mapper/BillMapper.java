package ru.hubabank.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hubabank.core.dto.BillDto;
import ru.hubabank.core.dto.ClientBillDto;
import ru.hubabank.core.entity.Bill;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BillMapper {

    @Mapping(target = "closed", expression = "java(entity.getClosingInstant() != null)")
    BillDto mapEntityToDto(Bill entity);

    ClientBillDto mapEntityToClientDto(Bill entity);
}
