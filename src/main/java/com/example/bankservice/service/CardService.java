package com.example.bankservice.service;

import com.example.bankservice.model.Card;

public interface CardService {

    Boolean validateCard(String cardNumber);

    Card findByCardNumber(String cardNumber);
}
