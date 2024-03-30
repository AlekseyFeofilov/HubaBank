package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.dto.TransactionDto;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transaction;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.mapper.TransactionMapper;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.repository.TransactionRepository;
import ru.hubabank.core.service.strategy.BillSearchStrategy;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @deprecated используйте {@link TransferService}
 */
@Deprecated(since = "1.2.2")
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final BalanceService balanceService;
    private final BillRepository billRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public @NotNull List<TransactionDto> getTransactions(@NotNull UUID userId, @NotNull UUID billId) {
        return billRepository.findById(billId)
                .filter(e -> e.getUserId().equals(userId))
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException)
                .getTransactions()
                .stream()
                .map(transactionMapper::mapEntityToDto)
                .toList();
    }

    public @NotNull List<TransactionDto> getTransactions(@NotNull UUID billId) {
        return billRepository.findById(billId)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException)
                .getTransactions()
                .stream()
                .map(transactionMapper::mapEntityToDto)
                .toList();
    }

    @Transactional
    public void createTransaction(
            long balanceChange,
            @NotNull BillSearchStrategy billSearchStrategy,
            @NotNull TransactionReason reason
    ) {
        if (balanceChange == 0) {
            throw ErrorType.TRANSACTION_WITH_ZERO_BALANCE_CHANGE.createException();
        }

        Bill bill = billSearchStrategy.findBill(billRepository)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException);
        balanceService.updateBalance(bill, balanceChange);

        Transaction transaction = transactionMapper.mapCreationDtoToEntity(balanceChange, reason);
        transaction.setBill(bill);
        transaction.setInstant(Instant.now());
        transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createTransaction(
            long balanceChange,
            @NotNull Bill bill,
            @NotNull TransactionReason reason
    ) {
        if (balanceChange == 0) {
            throw ErrorType.TRANSACTION_WITH_ZERO_BALANCE_CHANGE.createException();
        }

        balanceService.updateBalance(bill, balanceChange);

        Transaction transaction = transactionMapper.mapCreationDtoToEntity(balanceChange, reason);
        transaction.setBill(bill);
        transaction.setInstant(Instant.now());
        return transactionRepository.save(transaction);
    }
}
