package ru.hubabank.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RabbitConstants {

    public static final String TRANSFER_TO_BILL_QUEUE = "transfer_to_bill_request_queue";
    public static final String TRANSFER_TO_USER_QUEUE = "transfer_to_user_request_queue";
    public static final String DEPOSIT_QUEUE = "deposit_request_queue";
    public static final String WITHDRAWAL_QUEUE = "withdrawal_request_queue";
    public static final String TRANSACTION_EXCHANGE = "transfer_exchange";
    public static final String TRANSACTION_SUCCESS_ROUTING_KEY = "success";
    public static final String TRANSACTION_ERROR_ROUTING_KEY = "error";
}
