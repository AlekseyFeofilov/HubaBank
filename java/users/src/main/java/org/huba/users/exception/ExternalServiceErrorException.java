package org.huba.users.exception;

import org.springframework.http.HttpStatus;

public class ExternalServiceErrorException extends CustomException {

    public ExternalServiceErrorException(String message, HttpStatus status) {
        super(message, status);
    }

    public ExternalServiceErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ExternalServiceErrorException() {
        super("service error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
