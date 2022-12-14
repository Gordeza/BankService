package com.example.bankservice.repository;

import com.example.bankservice.model.Account;
import com.example.bankservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
