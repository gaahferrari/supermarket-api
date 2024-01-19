package com.supermaket.GMarket.exceptions;

import com.supermaket.GMarket.responses.BaseBodyError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
public class CustomHandlerException {
    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequest(BadRequestException exception){
        BaseBodyError error = BaseBodyError.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .code("400")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFound(NotFoundException exception){
        BaseBodyError error = BaseBodyError.builder()
                .error(HttpStatus.NOT_FOUND.name())
                .code("404")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        String errorMessage = errorMap.values().stream()
                .collect(Collectors.joining(", "));

        BaseBodyError error = BaseBodyError.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .code("400")
                .message(errorMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
