package com.example.bankservice.exception;

public class InvalidCardException extends RuntimeException{
    public InvalidCardException(String message) {
        super(message);
    }
}
