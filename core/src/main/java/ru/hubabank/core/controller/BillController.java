package ru.hubabank.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hubabank.core.dto.BillDto;
import ru.hubabank.core.dto.ClientBillDto;
import ru.hubabank.core.mapper.BillMapper;
import ru.hubabank.core.service.BillService;
import ru.hubabank.core.service.strategy.UserBillSearchStrategy;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.util.List;
import java.util.UUID;

import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_USER_SCHEME;
import static ru.hubabank.core.versioning.ApiVersion.MAX;
import static ru.hubabank.core.versioning.ApiVersion.MIN;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final BillMapper billMapper;

    @GetMapping(value = "bills")
    @ApiVersionRange(min = MIN, max = MAX)
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @PreAuthorize("hasAuthority('PRIVILEGE_BILL_READ_OTHERS')")
    @Operation(
            summary = "Посмотреть все счета",
            description = """
                    Описание прав:
                    * BILL_READ_OTHERS - доступ к любым счетам
                    """
    )
    public List<BillDto> getBills() {
        return billService.getBills();
    }

    @GetMapping("users/{userId}/bills")
    @ApiVersionRange(min = MIN, max = MAX)
    @PreAuthorize("(hasAuthority('PRIVILEGE_BILL_READ') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_BILL_READ_OTHERS')")
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @Operation(
            summary = "Посмотреть все счета пользователя",
            description = """
                    Описание прав:
                    * BILL_READ - доступ к своим счетам
                    * BILL_READ_OTHERS - доступ к счетам любого клиента
                    """
    )
    public List<ClientBillDto> getUserBills(
            @PathVariable("userId") UUID userId
    ) {
        return billService.getClientBills(userId);
    }

    @GetMapping("users/{userId}/bills/{billId}")
    @ApiVersionRange(min = MIN, max = MAX)
    @PreAuthorize("(hasAuthority('PRIVILEGE_BILL_READ') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_BILL_READ_OTHERS')")
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @Operation(
            summary = "Посмотреть информацию о счете пользователя",
            description = """
                    Описание прав:
                    * BILL_READ - доступ к информации о своем счете
                    * BILL_READ_OTHERS - доступ к информации о любом счете
                    """
    )
    public ClientBillDto getBill(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        return billMapper.mapEntityToClientDto(billService.getBill(UserBillSearchStrategy.of(userId, billId)));
    }

    @PostMapping("users/{userId}/bills")
    @ApiVersionRange(min = MIN, max = MAX)
    @PreAuthorize("(hasAuthority('PRIVILEGE_BILL_WRITE') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_BILL_WRITE_OTHERS')")
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @Operation(
            summary = "Создать счет для пользователя",
            description = """
                    Возвращает информацию о созданном счете.
                    
                    Описание прав:
                    * BILL_WRITE - доступ к открытию своего счета
                    * BILL_WRITE_OTHERS - доступ к открытию счета у любого клиента
                    """
    )
    public ClientBillDto createBill(
            @PathVariable("userId") UUID userId
    ) {
        return billService.createBill(userId);
    }

    @DeleteMapping("users/{userId}/bills/{billId}")
    @ApiVersionRange(min = MIN, max = MAX)
    @PreAuthorize("(hasAuthority('PRIVILEGE_BILL_WRITE') and #userId == authentication.principal.id) " +
            "or hasAuthority('PRIVILEGE_BILL_WRITE_OTHERS')")
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @Operation(
            summary = "Закрыть счет у пользователя",
            description = """
                    Описание прав:
                    * BILL_WRITE - доступ к закрытию своего счета
                    * BILL_WRITE_OTHERS - доступ к закрытию любого счета
                    """
    )
    public void closeBill(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        billService.closeBill(userId, billId);
    }
}
