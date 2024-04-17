package org.huba.auth.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {

    public BadRequestException(String message, HttpStatus status) {
        super(message, status);
    }

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException() {
        super("bad request", HttpStatus.BAD_REQUEST);
    }

}