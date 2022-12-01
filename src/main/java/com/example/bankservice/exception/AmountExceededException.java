package com.example.bankservice.exception;

public class AmountExceededException extends RuntimeException{
    public AmountExceededException(String message) {
        super(message);
    }
}
