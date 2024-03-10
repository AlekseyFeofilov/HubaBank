package ru.hubabank.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IntegrationException extends Exception {

    private final HttpStatus status;

    public IntegrationException(HttpStatus status) {
        this.status = status;
    }
}
