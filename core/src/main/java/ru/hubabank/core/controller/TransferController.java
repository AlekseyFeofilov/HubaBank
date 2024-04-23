package ru.hubabank.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.hubabank.core.dto.TransferDto;
import ru.hubabank.core.mapper.TransferMapper;
import ru.hubabank.core.service.TransferService;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.util.List;
import java.util.UUID;

import static ru.hubabank.core.constant.HeaderConstants.REQUEST_ID_HEADER;
import static ru.hubabank.core.versioning.ApiVersion.*;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final TransferMapper transferMapper;

    //<editor-fold desc="GET /bills/{billId}/transfers">
    @GetMapping("bills/{billId}/transfers")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Посмотреть историю переводов по счету")
    public List<TransferDto> getTransfersV2(
            @PathVariable("billId") UUID billId
    ) {
        return transferService.getTransfers(billId).stream()
                .map(transferMapper::mapEntityToDto)
                .toList();
    }

    @GetMapping("bills/{billId}/transfers")
    @ApiVersionRange(min = VERSION_3, max = MAX)
    @Operation(summary = "Посмотреть историю переводов по счету")
    @Parameter(
            in = ParameterIn.HEADER,
            name = REQUEST_ID_HEADER, description = "Идентификатор запроса для трассировки",
            required = true, schema = @Schema(type = "string")
    )
    public List<TransferDto> getTransfersV3(
            @PathVariable("billId") UUID billId
    ) {
        return transferService.getTransfers(billId).stream()
                .map(transferMapper::mapEntityToDto)
                .toList();
    }
    //</editor-fold>
}
