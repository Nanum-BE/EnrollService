package com.nanum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class OverlapException extends RuntimeException {
    public OverlapException(String message) {
        super(message);
    }
}
