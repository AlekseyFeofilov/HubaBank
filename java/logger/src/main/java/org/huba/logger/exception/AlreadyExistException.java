package org.huba.logger.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends CustomException {

    public AlreadyExistException(String message, HttpStatus status) {
        super(message, status);
    }

    public AlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public AlreadyExistException() {
        super("conflict", HttpStatus.CONFLICT);
    }
}
