package com.zbank.creditcard.repository;

import com.zbank.creditcard.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository
        extends JpaRepository<CreditCard, Long> {
}