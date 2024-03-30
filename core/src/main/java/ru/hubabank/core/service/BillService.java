package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.BillType;
import ru.hubabank.core.entity.CurrencyType;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.service.strategy.BillSearchStrategy;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    public @NotNull List<Bill> getBills() {
        return billRepository.findAll();
    }

    public @NotNull List<Bill> getClientBills(@NotNull UUID userId) {
        return billRepository.findAllByUserIdAndClosingInstantIsNull(userId);
    }

    public @NotNull Bill getBill(@NotNull BillSearchStrategy billSearchStrategy) {
        return billSearchStrategy.findBill(billRepository)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
    }

    public @NotNull Bill createBill(@NotNull UUID userId, @NotNull CurrencyType currency) {
        Bill bill = Bill.builder()
                .userId(userId)
                .balance(0)
                .currency(currency)
                .type(BillType.USER)
                .creationInstant(Instant.now())
                .build();
        return billRepository.save(bill);
    }

    /**
     * @deprecated используйте {@link #closeBill(UUID)}
     */
    @Deprecated(since = "1.2.2")
    @Transactional
    public void closeBill(@NotNull UUID userId, @NotNull UUID billId) {
        Bill bill = billRepository.findById(billId)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
        closeBill(bill);
    }

    @Transactional
    public void closeBill(@NotNull UUID billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
        closeBill(bill);
    }

    private void closeBill(Bill bill) {
        if (bill.getType() != BillType.USER) {
            throw ErrorType.CLOSING_SYSTEM_BILL.createException();
        }

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
