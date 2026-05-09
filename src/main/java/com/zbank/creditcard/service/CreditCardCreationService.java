package com.zbank.creditcard.service;

import com.zbank.creditcard.entity.ApplicationStatus;

public interface CreditCardCreationService {
    void createCard(ApplicationStatus application);
}
