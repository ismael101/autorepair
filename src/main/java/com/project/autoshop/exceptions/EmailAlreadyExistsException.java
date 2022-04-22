package com.project.autoshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//custom exception for when creating clients with duplicate emails
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException() {}

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}