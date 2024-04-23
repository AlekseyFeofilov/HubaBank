package org.huba.logger.exception;


public class BadLoginOrPasswordException extends AuthException{
    public BadLoginOrPasswordException() {
        super("bad credentials");
    }
}
