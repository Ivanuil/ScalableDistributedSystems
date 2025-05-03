package com.ivanuil.scalabledistributedsystems.web.exceptionhandler;

import com.ivanuil.scalabledistributedsystems.exception.BankAccountException;
import com.ivanuil.scalabledistributedsystems.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> exceptionHandler(RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(BankAccountException.class)
    public ResponseEntity<String> bankAccountExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

}
