package org.huba.users.exception;


public class BadLoginOrPasswordException extends AuthException{
    public BadLoginOrPasswordException() {
        super("bad credentials");
    }
}
