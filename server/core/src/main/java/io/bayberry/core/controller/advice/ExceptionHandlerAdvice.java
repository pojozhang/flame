package io.bayberry.core.controller.advice;

import io.bayberry.core.exception.BadRequestException;
import io.bayberry.core.common.Error;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = BadRequestException.class)
    private ResponseEntity<Error> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getError());
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    private ResponseEntity<Error> handleDuplicateKeyException(DuplicateKeyException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Error.RESOURCE_ALREADY_EXISTS);
    }
}
