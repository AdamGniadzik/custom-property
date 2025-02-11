package com.example.api;

import com.example.db.exceptions.CustomPropertyBindingNotExistsOrIsDisabled;
import com.example.db.exceptions.CustomPropertyNotExistsException;
import com.example.db.DatabaseConflictException;
import com.example.db.exceptions.DuplicatedCustomPropertyCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = { Exception.class })
    protected ResponseEntity<Object> handleUnexpectedError(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, "Unexpected error",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value
            = { org.jooq.exception.NoDataFoundException.class })
    protected ResponseEntity<Object> handleDataNotFoundException(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, "Invalid request, check correctness of provided data.",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { CustomPropertyBindingNotExistsOrIsDisabled.class, CustomPropertyNotExistsException.class, CustomPropertyNotExistsException.class, DuplicatedCustomPropertyCodeException.class})
    protected ResponseEntity<Object> handleCustomPropertyExceptions(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = { DatabaseConflictException.class })
    protected ResponseEntity<Object> handleDatabaseConflict(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
