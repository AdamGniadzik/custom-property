package com.example.api;

import com.example.db.DatabaseConflictException;
import com.example.db.ObjectNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<Object> handleUnexpectedError(
            Exception ex, WebRequest request) {
        ex.printStackTrace();

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value
            = { ObjectNotFoundException.class })
    protected ResponseEntity<Object> handleObjectNotfound(
            Exception ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = { DatabaseConflictException.class })
    protected ResponseEntity<Object> handleDatabaseConflict(
            Exception ex, WebRequest request) {
        ex.printStackTrace();

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
