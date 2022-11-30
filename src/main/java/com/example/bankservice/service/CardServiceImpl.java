package com.example.bankservice.service;

import com.example.bankservice.model.Card;
import com.example.bankservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{

    @Autowired
    private final CardRepository cardRepository;

    @Override
    public Boolean validateCard(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if(card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card doesn't belong to the bank");
        }
        if(!card.getIsNotLocked()) {
            throw new ResponseStatusException(HttpStatus.LOCKED, "Card is locked");
        }
        return true;
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }
}
