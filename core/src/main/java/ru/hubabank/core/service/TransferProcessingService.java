package ru.hubabank.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hubabank.core.amqp.client.TransactionClient;
import ru.hubabank.core.amqp.dto.TransferEntity;
import ru.hubabank.core.amqp.dto.response.TransferResponse;
import ru.hubabank.core.entity.Bill;
import ru.hubabank.core.entity.Transfer;
import ru.hubabank.core.error.ErrorType;
import ru.hubabank.core.repository.BillRepository;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferProcessingService {

    private final TransactionClient transactionClient;
    private final BillRepository billRepository;
    private final TransferService transferService;

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
        Transfer transfer = transferService.createTransfer(sourceBillId, targetBillId, amount);
        transactionClient.sendMessage(TransferResponse.builder()
                .id(transfer.getId())
                .source(TransferEntity.builder()
                        .billId(transfer.getSource().getId())
                        .userId(transfer.getSource().getUserId())
                        .type(transfer.getSource().getType())
                        .currency(transfer.getSource().getCurrency())
                        .build())
                .target(TransferEntity.builder()
                        .billId(transfer.getTarget().getId())
                        .userId(transfer.getTarget().getUserId())
                        .type(transfer.getTarget().getType())
                        .currency(transfer.getTarget().getCurrency())
                        .build())
                .amount(amount)
                .instant(Instant.now())
                .build()
        );
    }
}
