package com.example.bankservice.controller;

import com.example.bankservice.DAO.CardDAO;
import com.example.bankservice.DAO.JwtResponse;
import com.example.bankservice.repository.CardRepository;
import com.example.bankservice.security.jwt.JwtUtils;
import com.example.bankservice.security.services.CardDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BankController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final CardRepository cardRepository;

    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final JwtUtils jwtUtils;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@RequestBody CardDAO cardDAO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(cardDAO.getCardNumber(), cardDAO.getPin()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CardDetailsImpl userDetails = (CardDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(
                JwtResponse.builder()
                    .token(jwt)
                    .id(userDetails.getCard().getId())
                    .cardNumber(userDetails.getUsername())
                    .build()
        );
    }
}
