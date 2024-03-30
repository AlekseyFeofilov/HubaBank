package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transaction;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.entity.Transfer;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.integration.service.CurrencyService;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.repository.TransferRepository;
import ru.hubabank.core.service.strategy.SimpleBillSearchStrategy;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    @SuppressWarnings("deprecation")
    private final TransactionService transactionService;
    private final CurrencyService currencyService;
    private final TransferRepository transferRepository;
    private final BillRepository billRepository;

    public List<Transfer> getTransfers(UUID billId) {
        return transferRepository.findAllByBillId(billId);
    }

    @Transactional
    public Transfer createTransfer(UUID sourceBillId, UUID targetBillId, long amount) {
        Bill sourceBill = SimpleBillSearchStrategy.of(sourceBillId)
                .findBill(billRepository)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
        Bill targetBill = SimpleBillSearchStrategy.of(targetBillId)
                .findBill(billRepository)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);

        long withdrawal = -amount;
        long admission = amount;
        if (sourceBill.getCurrency() != targetBill.getCurrency()) {
            admission = currencyService.convertCurrency(
                    sourceBill.getCurrency().name(),
                    targetBill.getCurrency().name(),
                    amount
            );
        }

        Transaction source = transactionService.createTransaction(
                withdrawal,
                sourceBill,
                TransactionReason.TRANSFER
        );
        Transaction target = transactionService.createTransaction(
                admission,
                targetBill,
                TransactionReason.TRANSFER
        );
        return transferRepository.save(Transfer.builder()
                .amount(amount)
                .source(source.getBill())
                .target(target.getBill())
                .instant(Instant.now())
                .build());
    }
}
