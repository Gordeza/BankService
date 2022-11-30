package com.example.bankservice.controller;

import com.example.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankController {

    private final CardService cardService;
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateCard(@RequestParam String cardNumber) {
        return ResponseEntity.ok(cardService.validateCard(cardNumber));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam Double amount) {
        return null;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam Double amount) {
        return null;
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<Double> checkBalance() {
        return null;
    }
}
