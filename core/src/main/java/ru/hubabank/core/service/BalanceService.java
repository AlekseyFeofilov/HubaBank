package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BillRepository billRepository;

    @Transactional
    public void updateBalance(@NotNull Bill bill, long balanceChange) {
        if (bill.getClosingInstant() != null) {
            throw ErrorType.CLOSED_BILL_OPERATION.createException();
        }

        long newBalance = bill.getBalance() + balanceChange;

        if (newBalance < 0) {
            throw ErrorType.CANNOT_NEGATIVE_BILL_BALANCE.createException();
        }

        bill.setBalance(newBalance);
        billRepository.save(bill);
    }
}
