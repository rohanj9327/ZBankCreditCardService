package com.zbank.creditcard.repository;

import com.zbank.creditcard.entity.Applicants;
import com.zbank.creditcard.entity.CreditCardApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardApplicationRepository extends JpaRepository<CreditCardApplication, Long> {
}
