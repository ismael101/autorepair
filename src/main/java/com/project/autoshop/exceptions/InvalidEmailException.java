package com.project.autoshop.exceptions;

//custom exception for invalid email
public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException() {
    }

    public InvalidEmailException(String message) {
        super(message);
    }

    public InvalidEmailException(Throwable cause) {
        super(cause);
    }

    public InvalidEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
