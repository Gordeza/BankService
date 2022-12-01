package com.example.bankservice.controller;

import com.example.bankservice.exception.CardLockedException;
import com.example.bankservice.exception.InvalidCardException;
import com.example.bankservice.model.Card;
import com.example.bankservice.security.services.CardDetailsImpl;
import com.example.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final CardService cardService;
    @GetMapping("/validate")
    public ResponseEntity<?> validateCard(@RequestParam String cardNumber) throws InvalidCardException, CardLockedException {
        return ResponseEntity.ok(cardService.validateCard(cardNumber));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(Principal principal, @RequestParam Double amount) {
        Card card = ((CardDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getCard();
        return ResponseEntity.ok(cardService.deposit(card, amount));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(Principal principal, @RequestParam Double amount) {
        Card card = ((CardDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getCard();
        return ResponseEntity.ok(cardService.withdraw(card, amount));
    }

    @GetMapping("/balance")
    public ResponseEntity<?> checkBalance(Principal principal) {
        Card card = ((CardDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getCard();
        return ResponseEntity.ok(cardService.balance(card));
    }

//    @PutMapping("/updatePin")
//    public ResponseEntity<?> updatePin(Principal principal, @RequestParam String pin) {
//        Card card = ((CardDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getCard();
//        return ResponseEntity.ok(cardService.updatePin(card, pin));
//    }
}
