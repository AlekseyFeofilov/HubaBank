package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BillRepository billRepository;

    @Transactional
    public void updateBalance(@NotNull UUID userId, @NotNull UUID billId, long balanceChange) {
        Bill bill = billRepository.findById(billId)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
        long newBalance = bill.getBalance() + balanceChange;

        if (newBalance < 0) {
            throw ErrorType.CANNOT_NEGATIVE_BILL_BALANCE.createException();
        }

        bill.setBalance(newBalance);
        billRepository.save(bill);
    }
}