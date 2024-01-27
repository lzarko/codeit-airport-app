package com.example.airportapp.exception;

import org.springframework.util.StringUtils;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Class<?> clazz, String code) {
        super(generateMessage(clazz, code));
    }

    private static String generateMessage(Class<?> clazz, String code) {
        String entityType = StringUtils.capitalize(clazz.getSimpleName());
        return String.format("%s was not found for the following code %s", entityType, code);
    }
}
