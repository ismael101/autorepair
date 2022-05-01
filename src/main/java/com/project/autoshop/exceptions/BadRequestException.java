package com.project.autoshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//custom exception for not null values
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
