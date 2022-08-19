package com.sparta.matchgi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public ResponseEntity<?> handleApiRequestException(IllegalArgumentException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(400));

    }

    @ExceptionHandler(value = { NoSuchElementException.class })
    public ResponseEntity<?> handleApiRequestException(NoSuchElementException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(401));

    }
}

