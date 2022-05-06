package com.project.autoshop.exceptions;

//custom exception for not found
public class NotFoundException extends RuntimeException{
    public NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
