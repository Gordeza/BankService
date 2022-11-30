package com.example.bankservice.controller;

import com.example.bankservice.model.Card;
import com.example.bankservice.security.services.CardDetailsImpl;
import com.example.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final CardService cardService;
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateCard(@RequestParam String cardNumber) {
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
        return ResponseEntity.ok("2");
    }

    @GetMapping("/balance")
    public ResponseEntity<?> checkBalance(Principal principal) {
        Card card = ((CardDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getCard();
        return ResponseEntity.ok("3");
    }
}
