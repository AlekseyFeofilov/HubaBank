package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.dto.BillDto;
import ru.hubabank.core.dto.ClientBillDto;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.mapper.BillMapper;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.service.strategy.BillSearchStrategy;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    public @NotNull List<BillDto> getBills() {
        return billRepository.findAll()
                .stream()
                .map(billMapper::mapEntityToDto)
                .toList();
    }

    public @NotNull List<ClientBillDto> getClientBills(@NotNull UUID userId) {
        return billRepository.findAllByUserIdAndClosingInstantIsNull(userId)
                .stream()
                .map(billMapper::mapEntityToClientDto)
                .toList();
    }

    public @NotNull Bill getBill(@NotNull BillSearchStrategy billSearchStrategy) {
        return billSearchStrategy.findBill(billRepository)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
    }

    public @NotNull ClientBillDto createBill(@NotNull UUID userId) {
        Bill bill = Bill.builder()
                .userId(userId)
                .balance(0)
                .creationInstant(Instant.now())
                .build();
        return billMapper.mapEntityToClientDto(billRepository.save(bill));
    }

    @Transactional
    public void closeBill(@NotNull UUID userId, @NotNull UUID billId) {
        Bill bill = billRepository.findById(billId)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);

        if (bill.getBalance() > 0) {
            throw ErrorType.CLOSING_BILL_WITH_POSITIVE_BALANCE.createException();
        }
        else if (bill.getBalance() < 0) {
            throw ErrorType.CLOSING_BILL_WITH_NEGATIVE_BALANCE.createException();
        }

        bill.setClosingInstant(Instant.now());
        billRepository.save(bill);
    }
}
