package com.example.airportapp.exception.handling;

import com.example.airportapp.exception.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ApiSubError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::buildApiValidationError)
                .collect(Collectors.toList());
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation error", validationErrors);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiError> handleConstraintViolations (ConstraintViolationException ex) {
        List<ApiSubError> validationErrors = ex.getConstraintViolations().stream()
                .map(constraintViolation -> ApiValidationError.builder()
                        .field(constraintViolation.getPropertyPath().toString())
                        .rejectedValue(constraintViolation.getInvalidValue())
                        .message(constraintViolation.getMessage())
                        .build())
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Validation error", validationErrors);
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    private ApiValidationError buildApiValidationError(FieldError fieldError) {
        return ApiValidationError.builder()
                .field(fieldError.getField())
                .rejectedValue(fieldError.getRejectedValue())
                .message(fieldError.getDefaultMessage())
                .build();
    }
}
