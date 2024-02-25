package org.huba.users.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {

    public NotFoundException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException() {
        super("not found", HttpStatus.NOT_FOUND);
    }
}
