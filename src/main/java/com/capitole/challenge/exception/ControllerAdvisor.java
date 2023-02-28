package com.capitole.challenge.exception;

import com.capitole.challenge.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handlePriceNotFoundException(PriceNotFoundException ex, WebRequest request) {
        Error message = new Error();
        message.setCode(HttpStatus.BAD_REQUEST.value());
        message.setMessage(ex.toString());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleRunTimeException(Exception ex, WebRequest request) {
        Error message = new Error();
        message.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.setMessage(ex.toString());
        return ResponseEntity.internalServerError().body(message);
    }
}
