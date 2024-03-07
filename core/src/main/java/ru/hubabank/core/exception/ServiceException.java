package ru.hubabank.core.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import ru.hubabank.core.error.ErrorType;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;
    private final int code;

    public ServiceException(HttpStatus status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public static @NotNull ServiceException of(@NotNull ErrorType errorType) {
        return new ServiceException(errorType.getStatus(), errorType.getCode(), errorType.getMessage());
    }

    public static @NotNull ServiceException of(@NotNull ErrorType errorType, @NotNull Object... args) {
        String message = String.format(errorType.getMessage(), args);
        return new ServiceException(errorType.getStatus(), errorType.getCode(), message);
    }
}
