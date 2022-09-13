package com.ismael.autorepair.exceptions;

public class AlreadyExists extends RuntimeException{
    public AlreadyExists() {
    }

    public AlreadyExists(String message) {
        super(message);
    }

    public AlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExists(Throwable cause) {
        super(cause);
    }

    public AlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
