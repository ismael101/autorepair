package com.project.autoshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//custom exception for not null values
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException() {}

    public EmptyFieldException(String message) {
        super(message);
    }

    public EmptyFieldException(Throwable cause) {
        super(cause);
    }

    public EmptyFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
