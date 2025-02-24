package com.ivanuil.scalabledistributedsystems.web.exceptionhandler;

import com.ivanuil.scalabledistributedsystems.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> exceptionHandler(RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

}
