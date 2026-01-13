package com.e_commerce.e_app.exception;

public class NotAuthenticationUserException extends AuthenticationException {
    public NotAuthenticationUserException(String message) {
        super(message);
    }
}
