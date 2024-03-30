package ru.hubabank.core.amqp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;
import ru.hubabank.core.amqp.dto.response.TransferErrorResponse;
import ru.hubabank.core.amqp.dto.response.TransferResponse;

import static ru.hubabank.core.constant.RabbitConstants.*;

@Component
@RequiredArgsConstructor
public class TransactionClient {

    private final AmqpTemplate amqpTemplate;

    public void sendMessage(TransferResponse transfer) {
        amqpTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_SUCCESS_ROUTING_KEY, transfer);
    }

    public void sendMessage(TransferErrorResponse error) {
        amqpTemplate.convertAndSend(TRANSACTION_EXCHANGE, TRANSACTION_ERROR_ROUTING_KEY, error);
    }
}
