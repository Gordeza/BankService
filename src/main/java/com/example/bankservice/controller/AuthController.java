package com.example.bankservice.controller;

import com.example.bankservice.DAO.JwtResponse;
import com.example.bankservice.model.Card;
import com.example.bankservice.security.jwt.JwtUtils;
import com.example.bankservice.security.services.CardDetailsImpl;
import com.example.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final CardService cardService;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestParam String cardNumber, @RequestParam String pin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(cardNumber, pin));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CardDetailsImpl userDetails = (CardDetailsImpl) authentication.getPrincipal();
        Card c = cardService.getCard(cardNumber);
        c.setFailedAttempts(0);
        cardService.save(c);
        return ResponseEntity.ok(
                JwtResponse.builder()
                    .token(jwt)
                    .id(userDetails.getCard().getId())
                    .cardNumber(userDetails.getUsername())
                    .build()
        );
    }
}
