package com.example.airportapp.exception.handling;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(callSuper = false)
public class ApiValidationError extends ApiSubError {

    String field;

    Object rejectedValue;

    String message;

}
