package org.huba.auth.exception;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends CustomException {

    public NotImplementedException(String message, HttpStatus status) {
        super(message, status);
    }

    public NotImplementedException(String message) {
        super(message, HttpStatus.NOT_IMPLEMENTED);
    }

    public NotImplementedException() {
        super("not implemented", HttpStatus.NOT_IMPLEMENTED);
    }
}
