package ru.hubabank.core.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import ru.hubabank.core.exception.ServiceException;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {
    UNKNOWN(HttpStatus.BAD_REQUEST, 0,
            "Неизвестная ошибка"),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, 1,
            "Ошибка подключения к смежным службам"),

    CANNOT_NEGATIVE_BILL_BALANCE(HttpStatus.BAD_REQUEST, 100,
            "Баланс счета не может уйти в минус"),
    CLOSING_BILL_WITH_POSITIVE_BALANCE(HttpStatus.BAD_REQUEST, 101,
            "Нельзя закрыть счет с положительным балансом"),
    CLOSING_BILL_WITH_NEGATIVE_BALANCE(HttpStatus.BAD_REQUEST, 102,
            "Нельзя закрыть счет с отрицательным балансом"),
    TRANSACTION_WITH_ZERO_BALANCE_CHANGE(HttpStatus.BAD_REQUEST, 103,
            "Транзакция не может быть с нулевой суммой изменения"),

    BILL_NOT_FOUND(HttpStatus.NOT_FOUND, 200,
            "Счет не найден"),
    CLOSED_BILL_OPERATION(HttpStatus.NOT_FOUND, 201,
            "Нельзя совершать операции над закрытым счетом");

    private final HttpStatus status;
    private final int code;
    private final String message;

    public ServiceException createException() {
        return ServiceException.of(this);
    }

    public ServiceException createException(Throwable cause) {
        return ServiceException.of(this, cause);
    }
}
