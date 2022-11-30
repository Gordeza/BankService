package com.example.bankservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private String expMonth;

    private String expYear;

    @NotNull()
    private Boolean isNotLocked;

    private Integer failedAttempts;

    private String pin;

    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonManagedReference
    @JsonIgnore
    private Account account;
}

