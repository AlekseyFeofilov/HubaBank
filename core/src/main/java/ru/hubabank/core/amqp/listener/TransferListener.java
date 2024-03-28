package ru.hubabank.core.amqp.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.hubabank.core.amqp.client.TransactionClient;
import ru.hubabank.core.amqp.dto.TransferEntity;
import ru.hubabank.core.amqp.dto.request.DepositRequest;
import ru.hubabank.core.amqp.dto.request.TransferToBillRequest;
import ru.hubabank.core.amqp.dto.request.TransferToUserRequest;
import ru.hubabank.core.amqp.dto.request.WithdrawalRequest;
import ru.hubabank.core.amqp.dto.response.TransferErrorResponse;
import ru.hubabank.core.exception.ServiceException;
import ru.hubabank.core.service.TransferService;

import java.time.Instant;

import static ru.hubabank.core.constant.RabbitConstants.*;

@Component
@RequiredArgsConstructor
public class TransferListener {

    private final TransferService transferService;
    private final TransactionClient transactionClient;

    @RabbitListener(queues = DEPOSIT_QUEUE)
    public void processDeposit(DepositRequest deposit) {
        try {
            transferService.deposit(
                    deposit.getBillId(),
                    deposit.getAmount()
            );
        } catch (ServiceException e) {
            transactionClient.sendMessage(TransferErrorResponse.builder()
                    .errorType(e.getType())
                    .errorMessage(e.getMessage())
                    .target(TransferEntity.builder()
                            .billId(deposit.getBillId())
                            .build())
                    .amount(deposit.getAmount())
                    .instant(Instant.now())
                    .build()
            );
        }
    }

    @RabbitListener(queues = WITHDRAWAL_QUEUE)
    public void processWithdrawal(WithdrawalRequest withdrawal) {
        try {
            transferService.withdraw(
                    withdrawal.getBillId(),
                    withdrawal.getAmount()
            );
        } catch (ServiceException e) {
            transactionClient.sendMessage(TransferErrorResponse.builder()
                    .errorType(e.getType())
                    .errorMessage(e.getMessage())
                    .source(TransferEntity.builder()
                            .billId(withdrawal.getBillId())
                            .build())
                    .amount(withdrawal.getAmount())
                    .instant(Instant.now())
                    .build()
            );
        }
    }

    @RabbitListener(queues = TRANSFER_TO_BILL_QUEUE)
    public void processTransferToBill(TransferToBillRequest transfer) {
        try {
            transferService.transferToBill(
                    transfer.getSourceBillId(),
                    transfer.getTargetBillId(),
                    transfer.getAmount()
            );
        } catch (ServiceException e) {
            transactionClient.sendMessage(TransferErrorResponse.builder()
                    .errorType(e.getType())
                    .errorMessage(e.getMessage())
                    .source(TransferEntity.builder()
                            .billId(transfer.getSourceBillId())
                            .build())
                    .target(TransferEntity.builder()
                            .billId(transfer.getTargetBillId())
                            .build())
                    .amount(transfer.getAmount())
                    .instant(Instant.now())
                    .build()
            );
        }
    }

    @RabbitListener(queues = TRANSFER_TO_USER_QUEUE)
    public void processTransferToUser(TransferToUserRequest transfer) {
        try {
            transferService.transferToUser(
                    transfer.getSourceBillId(),
                    transfer.getTargetUserId(),
                    transfer.getAmount()
            );
        } catch (ServiceException e) {
            transactionClient.sendMessage(TransferErrorResponse.builder()
                    .errorType(e.getType())
                    .errorMessage(e.getMessage())
                    .source(TransferEntity.builder()
                            .billId(transfer.getSourceBillId())
                            .build())
                    .target(TransferEntity.builder()
                            .userId(transfer.getTargetUserId())
                            .build())
                    .amount(transfer.getAmount())
                    .instant(Instant.now())
                    .build()
            );
        }
    }
}
