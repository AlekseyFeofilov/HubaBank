package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.amqp.client.TransactionClient;
import ru.hubabank.core.amqp.dto.TransferEntity;
import ru.hubabank.core.amqp.dto.response.TransferResponse;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transaction;
import ru.hubabank.core.entity.TransactionReason;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;
import ru.hubabank.core.service.strategy.SimpleBillSearchStrategy;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransactionService transactionService;
    private final TransactionClient transactionClient;
    private final BillRepository billRepository;

    @Transactional
    public void deposit(UUID billId, long amount) {
        Bill bill = billRepository.findTerminalBill();
        transfer(bill.getId(), billId, amount);
    }

    @Transactional
    public void withdraw(UUID billId, long amount) {
        Bill bill = billRepository.findTerminalBill();
        transfer(billId, bill.getId(), amount);
    }

    @Transactional
    public void transferToUser(UUID sourceBillId, UUID targetUserId, long amount) {
        UUID targetBillId = billRepository.findMainUserBill(targetUserId)
                .orElseThrow(ErrorType.BILL_NOT_FOUND::createException).getId();
        transfer(sourceBillId, targetBillId, amount);
    }

    @Transactional
    public void transferToBill(UUID sourceBillId, UUID targetBillId, long amount) {
        transfer(sourceBillId, targetBillId, amount);
    }

    private void transfer(UUID sourceBillId, UUID targetBillId, long amount) {
        Transaction source = transactionService.createTransaction(
                -amount,
                SimpleBillSearchStrategy.of(sourceBillId),
                TransactionReason.TRANSFER
        );
        Transaction target = transactionService.createTransaction(
                amount,
                SimpleBillSearchStrategy.of(targetBillId),
                TransactionReason.TRANSFER
        );
        transactionClient.sendMessage(TransferResponse.builder()
                .source(TransferEntity.builder()
                        .transactionId(source.getId())
                        .billId(source.getBill().getId())
                        .userId(source.getBill().getUserId())
                        .type(source.getBill().getType())
                        .build())
                .target(TransferEntity.builder()
                        .transactionId(target.getId())
                        .billId(target.getBill().getId())
                        .userId(target.getBill().getUserId())
                        .type(target.getBill().getType())
                        .build())
                .amount(amount)
                .instant(Instant.now())
                .build()
        );
    }
}
