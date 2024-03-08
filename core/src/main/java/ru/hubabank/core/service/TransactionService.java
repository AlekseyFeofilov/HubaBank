package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.dto.TransactionCreationDto;
import ru.hubabank.core.dto.TransactionDto;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transaction;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.mapper.TransactionMapper;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.repository.TransactionRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void createTransaction(
            @NotNull UUID userId,
            @NotNull UUID billId,
            @NotNull TransactionCreationDto creationDto
    ) {
        if (creationDto.getBalanceChange() == 0) {
            throw ErrorType.TRANSACTION_WITH_ZERO_BALANCE_CHANGE.createException();
        }

        balanceService.updateBalance(userId, billId, creationDto.getBalanceChange());

        Transaction transaction = transactionMapper.mapCreationDtoToEntity(creationDto);
        transaction.setBill(Bill.builder().id(billId).build());
        transaction.setInstant(Instant.now());
        transactionRepository.save(transaction);
    }
}
