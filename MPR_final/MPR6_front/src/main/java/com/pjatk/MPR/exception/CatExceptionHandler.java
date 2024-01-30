package com.pjatk.MPR.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CatExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CatNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(RuntimeException runtimeException, WebRequest request) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CatIdTakenException.class)
    public ResponseEntity<Object> idTaken(RuntimeException runtimeException, WebRequest request) {
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
