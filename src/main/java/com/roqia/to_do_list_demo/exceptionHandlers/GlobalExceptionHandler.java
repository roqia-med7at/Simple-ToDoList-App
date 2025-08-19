package com.roqia.to_do_list_demo.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<?>handleRecordNotFoundException(RecordNotFoundException ex){
       ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(), Arrays.asList(ex.getMessage()));
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?>handleIncorrectPasswordException(IncorrectPasswordException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getLocalizedMessage(),Arrays.asList(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
