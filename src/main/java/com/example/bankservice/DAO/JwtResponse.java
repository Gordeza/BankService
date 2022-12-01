package com.example.bankservice.DAO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
  private String token;
  private final String type = "Bearer";
  private Long id;
  private String cardNumber;
}
