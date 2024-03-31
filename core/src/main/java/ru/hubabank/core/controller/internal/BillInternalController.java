package ru.hubabank.core.controller.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hubabank.core.dto.BillDtoV1;
import ru.hubabank.core.mapper.BillMapper;
import ru.hubabank.core.security.annotation.HasInternalRole;
import ru.hubabank.core.service.BillService;
import ru.hubabank.core.service.strategy.SimpleBillSearchStrategy;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.util.UUID;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_INTERNAL_SCHEME;
import static ru.hubabank.core.versioning.ApiVersion.MIN;
import static ru.hubabank.core.versioning.ApiVersion.VERSION_1;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal")
@Tag(name = "internal")
public class BillInternalController {

    private final BillService billService;
    private final BillMapper billMapper;

    @GetMapping("bills/{billId}")
    @ApiVersionRange(min = MIN, max = VERSION_1)
    @HasInternalRole
    @SecurityRequirement(name = SECURITY_INTERNAL_SCHEME)
    @Operation(
            summary = "Посмотреть информацию о счете пользователя",
            description = "Этот метод предназначен для внутреннего сервиса 'Кредиты'"
    )
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public BillDtoV1 getBill(@PathVariable("billId") UUID billId) {
        return billMapper.mapEntityToDtoV1(billService.getBill(SimpleBillSearchStrategy.of(billId)));
    }
}
