package com.example.bankservice.Init;


import com.example.bankservice.model.Account;
import com.example.bankservice.model.Card;
import com.example.bankservice.repository.AccountRepository;
import com.example.bankservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDB {
    private final CardRepository cardRepository;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initialiseDB() {
        List<Card> cards = new ArrayList<>();
        cards.add(Card.builder()
                .cardNumber("1234123412341234")
                .expMonth("05")
                .expYear("25")
                .pin(passwordEncoder.encode("1234"))
                .isNotLocked(true)
                .build());
        cards.add(Card.builder()
                .cardNumber("0123456789123456")
                .expMonth("10")
                .expYear("24")
                .pin(passwordEncoder.encode("1234"))
                .isNotLocked(true)
                .build());
        cards.add(Card.builder()
                .cardNumber("0000000000000000")
                .expMonth("01")
                .expYear("26")
                .pin(passwordEncoder.encode("0000"))
                .isNotLocked(false)
                .build());
        cardRepository.saveAll(cards);

        List<Account> accounts = new ArrayList<>();
//        accounts.add(Account.builder().balance(1000d).card(cards.get(0)).build());
//        accounts.add(Account.builder().balance(2000d).card(cards.get(1)).build());
//        accounts.add(Account.builder().balance(5000d).card(cards.get(2)).build());
        accounts.add(Account.builder().balance(1000d).build());
        accounts.add(Account.builder().balance(2000d).build());
        accounts.add(Account.builder().balance(5000d).build());
        accountRepository.saveAll(accounts);

        cards = cardRepository.findAll();
        for(int i = 0; i < cards.size(); i++) {
            cards.get(i).setAccount(accounts.get(i));
        }
        cardRepository.saveAll(cards);
    }
}

