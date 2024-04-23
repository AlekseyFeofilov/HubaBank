package org.huba.auth.exception;


public class BadLoginOrPasswordException extends AuthException{
    public BadLoginOrPasswordException() {
        super("bad credentials");
    }
}
