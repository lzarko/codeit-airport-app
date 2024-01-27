package com.example.airportapp.exception.handling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus httpStatus;

    private LocalDateTime timestamp;

    private String message;

    private List<ApiSubError> subErrors;

    private ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiError(HttpStatus httpStatus, String message, List<ApiSubError> subErrors) {
        this(httpStatus, message);
        this.subErrors = subErrors;
    }
}
