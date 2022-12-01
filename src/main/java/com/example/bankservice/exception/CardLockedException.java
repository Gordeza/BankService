package com.example.bankservice.exception;

public class CardLockedException extends RuntimeException{
    public CardLockedException(String message) {
        super(message);
    }
}
