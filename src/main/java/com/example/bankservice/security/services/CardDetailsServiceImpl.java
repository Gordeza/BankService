package com.example.bankservice.security.services;


import com.example.bankservice.model.Card;
import com.example.bankservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private final CardRepository cardRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Card card = cardRepository.findByCardNumber(username);
    if(card == null) throw new UsernameNotFoundException("Card Not Found with number: " + username);

    return new CardDetailsImpl(card);
  }

}
