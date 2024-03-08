package org.huba.users.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CustomException {

    public ForbiddenException(String message, HttpStatus status) {
        super(message, status);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException() {
        super("forbidden", HttpStatus.FORBIDDEN);
    }

}
