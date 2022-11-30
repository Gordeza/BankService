package com.example.bankservice.security.services;

import com.example.bankservice.model.Card;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Data
public class CardDetailsImpl implements UserDetails {

  private Card card;

  public CardDetailsImpl (Card card) {
    this.card = card;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return card.getPin();
  }

  @Override
  public String getUsername() {
    return card.getCardNumber();
  }

  @Override
  public boolean isAccountNonExpired() {
    //TODO calculate if expiration date is passed
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return card.getIsNotLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
