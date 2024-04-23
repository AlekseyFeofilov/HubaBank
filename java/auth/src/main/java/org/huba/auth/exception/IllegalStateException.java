package org.huba.auth.exception;

import org.springframework.http.HttpStatus;

public class IllegalStateException extends CustomException {

    public IllegalStateException(String message, HttpStatus status) {
        super(message, status);
    }

    public IllegalStateException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public IllegalStateException() {
        super("illegal state", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
