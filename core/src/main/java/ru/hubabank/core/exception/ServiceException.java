package ru.hubabank.core.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import ru.hubabank.core.error.ErrorType;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorType type;

    public ServiceException(HttpStatus status, ErrorType type, String message) {
        super(message);
        this.status = status;
        this.type = type;
    }

    public ServiceException(HttpStatus status, ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.type = type;
    }

    public static @NotNull ServiceException of(@NotNull ErrorType errorType) {
        return new ServiceException(errorType.getStatus(), errorType, errorType.getMessage());
    }

    public static @NotNull ServiceException of(@NotNull ErrorType errorType, @NotNull Throwable cause) {
        return new ServiceException(errorType.getStatus(), errorType, errorType.getMessage(), cause);
    }
}
