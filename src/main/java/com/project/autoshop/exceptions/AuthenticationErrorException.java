package com.project.autoshop.exceptions;


public class AuthenticationErrorException extends RuntimeException{
    public AuthenticationErrorException() {}

    public AuthenticationErrorException(String message) {
        super(message);
    }

    public AuthenticationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationErrorException(Throwable cause) {
        super(cause);
    }

    public AuthenticationErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
