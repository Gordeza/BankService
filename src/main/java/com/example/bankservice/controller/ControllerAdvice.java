package com.example.bankservice.controller;

import com.example.bankservice.DAO.ExceptionMessage;
import com.example.bankservice.exception.AmountExceededException;
import com.example.bankservice.exception.CardLockedException;
import com.example.bankservice.exception.InvalidCardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(AmountExceededException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionMessage amountExceeded(AmountExceededException ex, WebRequest request) {
        return new ExceptionMessage(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(CardLockedException.class)
    @ResponseStatus(value = HttpStatus.LOCKED)
    public ExceptionMessage cardLocked(CardLockedException ex, WebRequest request) {
        return new ExceptionMessage(
                HttpStatus.LOCKED.value(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(InvalidCardException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionMessage invalidCard(InvalidCardException ex, WebRequest request) {
        return new ExceptionMessage(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false));
    }
}
