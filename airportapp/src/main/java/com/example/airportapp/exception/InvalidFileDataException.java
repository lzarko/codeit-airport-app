package com.example.airportapp.exception;

public class InvalidFileDataException extends RuntimeException{

    private static final String ERROR_MESSAGE = "Exception has occurred while working with files. Check logs for more information";

    public InvalidFileDataException() {super(ERROR_MESSAGE);}
}
