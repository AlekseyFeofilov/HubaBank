package ru.hubabank.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hubabank.core.dto.BillDtoV1;
import ru.hubabank.core.dto.BillDtoV2;
import ru.hubabank.core.dto.ClientBillDtoV1;
import ru.hubabank.core.dto.ClientBillDtoV2;
import ru.hubabank.core.entity.Bill;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BillMapper {

    @Mapping(target = "closed", expression = "java(entity.getClosingInstant() != null)")
    BillDtoV1 mapEntityToDtoV1(Bill entity);

    @Mapping(target = "closed", expression = "java(entity.getClosingInstant() != null)")
    BillDtoV2 mapEntityToDtoV2(Bill entity);

    ClientBillDtoV1 mapEntityToClientDtoV1(Bill entity);

    ClientBillDtoV2 mapEntityToClientDtoV2(Bill entity);
}
