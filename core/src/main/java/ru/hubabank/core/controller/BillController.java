package ru.hubabank.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.hubabank.core.dto.*;
import ru.hubabank.core.entity.CurrencyType;
import ru.hubabank.core.mapper.BillMapper;
import ru.hubabank.core.service.BillService;
import ru.hubabank.core.service.strategy.SimpleBillSearchStrategy;
import ru.hubabank.core.service.strategy.UserBillSearchStrategy;
import ru.hubabank.core.versioning.ApiVersionRange;

import java.util.List;
import java.util.UUID;

import static ru.hubabank.core.constant.HeaderConstants.REQUEST_ID_HEADER;
import static ru.hubabank.core.constant.SwaggerConstants.SECURITY_USER_SCHEME;
import static ru.hubabank.core.versioning.ApiVersion.*;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final BillMapper billMapper;

    @GetMapping(value = "bills")
    @ApiVersionRange(min = MIN, max = VERSION_1)
    @SecurityRequirement(name = SECURITY_USER_SCHEME)
    @PreAuthorize("hasAuthority('PRIVILEGE_BILL_READ_OTHERS')")
    @Operation(
            summary = "Посмотреть все счета",
            description = """
                    Описание прав:
                    * BILL_READ_OTHERS - доступ к любым счетам
                    """
    )
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public List<BillDtoV1> getBillsV1() {
        return billService.getBills()
                .stream()
                .map(billMapper::mapEntityToDtoV1)
                .toList();
    }

    @GetMapping(value = "bills")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Посмотреть все счета")
    public List<BillDtoV2> getBillsV2() {
        return billService.getBills()
                .stream()
                .map(billMapper::mapEntityToDtoV2)
                .toList();
    }

    @GetMapping(value = "bills")
    @ApiVersionRange(min = VERSION_3, max = MAX)
    @Operation(summary = "Посмотреть все счета")
    public List<BillDtoV2> getBillsV3(
            @RequestHeader(REQUEST_ID_HEADER) UUID requestId
    ) {
        return billService.getBills()
                .stream()
                .map(billMapper::mapEntityToDtoV2)
                .toList();
    }

    @GetMapping("users/{userId}/bills")
    @ApiVersionRange(min = MIN, max = VERSION_1)
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
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public List<ClientBillDtoV1> getUserBillsV1(
            @PathVariable("userId") UUID userId
    ) {
        return billService.getClientBills(userId).stream()
                .map(billMapper::mapEntityToClientDtoV1)
                .toList();
    }

    @GetMapping("users/{userId}/bills")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Посмотреть все счета пользователя")
    public List<ClientBillDtoV2> getUserBillsV2(
            @PathVariable("userId") UUID userId
    ) {
        return billService.getClientBills(userId).stream()
                .map(billMapper::mapEntityToClientDtoV2)
                .toList();
    }

    @GetMapping("users/{userId}/bills")
    @ApiVersionRange(min = VERSION_3, max = MAX)
    @Operation(summary = "Посмотреть все счета пользователя")
    public List<ClientBillDtoV2> getUserBillsV3(
            @RequestHeader(REQUEST_ID_HEADER) UUID requestId,
            @PathVariable("userId") UUID userId
    ) {
        return billService.getClientBills(userId).stream()
                .map(billMapper::mapEntityToClientDtoV2)
                .toList();
    }

    @GetMapping("users/{userId}/bills/{billId}")
    @ApiVersionRange(min = MIN, max = VERSION_1)
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
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public ClientBillDtoV1 getUserBillV1(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        return billMapper.mapEntityToClientDtoV1(billService.getBill(UserBillSearchStrategy.of(userId, billId)));
    }

    /**
     * @deprecated используйте {@link #getBillV2(UUID)}
     */
    @Deprecated(since = "1.2.2")
    @GetMapping("users/{userId}/bills/{billId}")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Посмотреть информацию о счете пользователя")
    public ClientBillDtoV2 getUserBillV2(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        return billMapper.mapEntityToClientDtoV2(billService.getBill(UserBillSearchStrategy.of(userId, billId)));
    }

    @GetMapping("bills/{billId}")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Посмотреть информацию о счете")
    public BillDtoV2 getBillV2(@PathVariable("billId") UUID billId) {
        return billMapper.mapEntityToDtoV2(billService.getBill(SimpleBillSearchStrategy.of(billId)));
    }

    @GetMapping("bills/{billId}")
    @ApiVersionRange(min = VERSION_3, max = MAX)
    @Operation(summary = "Посмотреть информацию о счете")
    public BillDtoV2 getBillV3(
            @RequestHeader(REQUEST_ID_HEADER) UUID requestId,
            @PathVariable("billId") UUID billId
    ) {
        return billMapper.mapEntityToDtoV2(billService.getBill(SimpleBillSearchStrategy.of(billId)));
    }

    @PostMapping("users/{userId}/bills")
    @ApiVersionRange(min = MIN, max = VERSION_1)
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
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public ClientBillDtoV1 createBillV1(
            @PathVariable("userId") UUID userId
    ) {
        return billMapper.mapEntityToClientDtoV1(billService.createBill(userId, CurrencyType.RUB));
    }

    @PostMapping("users/{userId}/bills")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(
            summary = "Создать счет для пользователя",
            description = "Возвращает информацию о созданном счете."
    )
    public ClientBillDtoV2 createBillV2(
            @PathVariable("userId") UUID userId,
            @RequestBody BillCreationDto billCreationDto
    ) {
        return billMapper.mapEntityToClientDtoV2(billService.createBill(userId, billCreationDto.getCurrency()));
    }

    @PostMapping("users/{userId}/bills")
    @ApiVersionRange(min = VERSION_3, max = MAX)
    @Operation(
            summary = "Создать счет для пользователя",
            description = "Возвращает информацию о созданном счете."
    )
    public ClientBillDtoV2 createBillV3(
            @RequestHeader(REQUEST_ID_HEADER) UUID requestId,
            @PathVariable("userId") UUID userId,
            @RequestBody BillCreationDto billCreationDto
    ) {
        return billMapper.mapEntityToClientDtoV2(billService.createBill(userId, billCreationDto.getCurrency()));
    }

    @SuppressWarnings("deprecation")
    @DeleteMapping("users/{userId}/bills/{billId}")
    @ApiVersionRange(min = MIN, max = VERSION_1)
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
    @ApiResponse(responseCode = "401", content = @Content(), description = "Не авторизован")
    @ApiResponse(responseCode = "403", content = @Content(), description = "Недостаточно прав")
    public void closeUserBillV1(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        billService.closeBill(userId, billId);
    }

    /**
     * @deprecated используйте {@link #closeBillV2(UUID)}
     */
    @Deprecated(since = "1.2.2")
    @DeleteMapping("users/{userId}/bills/{billId}")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Закрыть счет у пользователя")
    public void closeUserBillV2(
            @PathVariable("userId") UUID userId,
            @PathVariable("billId") UUID billId
    ) {
        billService.closeBill(userId, billId);
    }

    @DeleteMapping("bills/{billId}")
    @ApiVersionRange(min = VERSION_2, max = VERSION_2)
    @Operation(summary = "Закрыть счет")
    public void closeBillV2(
            @PathVariable("billId") UUID billId
    ) {
        billService.closeBill(billId);
    }

    @DeleteMapping("bills/{billId}")
    @ApiVersionRange(min = VERSION_3, max = MAX)
    @Operation(summary = "Закрыть счет")
    public void closeBillV3(
            @RequestHeader(REQUEST_ID_HEADER) UUID requestId,
            @PathVariable("billId") UUID billId
    ) {
        billService.closeBill(billId);
    }
}
