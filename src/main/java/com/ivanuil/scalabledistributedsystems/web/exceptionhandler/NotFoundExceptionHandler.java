package com.ivanuil.scalabledistributedsystems.web.exceptionhandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> exceptionHandler(RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

}
