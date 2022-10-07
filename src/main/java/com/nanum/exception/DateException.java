package com.nanum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class DateException extends RuntimeException {
    public DateException(String message) {
        super(message);
    }
}
