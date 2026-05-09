package com.zbank.creditcard.service;

import com.zbank.creditcard.dto.request.FirstLoginRequestDto;
import com.zbank.creditcard.dto.response.FirstLoginResponseDto;
import com.zbank.creditcard.entity.CreditCard;
import com.zbank.creditcard.repository.CreditCardRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FirstLoginService {

    private final CreditCardRepository creditCardRepository;
    private final PasswordEncoder passwordEncoder;

    public FirstLoginService(CreditCardRepository creditCardRepository,
                             PasswordEncoder passwordEncoder) {
        this.creditCardRepository = creditCardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public FirstLoginResponseDto generatePin(FirstLoginRequestDto request) {
        if (!request.getNewPin().equals(request.getConfirmPin())) {
            throw new RuntimeException("New PIN and confirm PIN do not match");
        }

        CreditCard creditCard = creditCardRepository.findByCardNumber(request.getCardNumber())
                .orElseThrow(() -> new RuntimeException("Invalid credit card number"));

        if (!Boolean.TRUE.equals(creditCard.getFirstLogin())) {
            throw new RuntimeException("First time login already completed");
        }

        if (!passwordEncoder.matches(request.getFirstTimePin(), creditCard.getEncryptedPin())) {
            throw new RuntimeException("Invalid first time PIN");
        }

        creditCard.setEncryptedPin(passwordEncoder.encode(request.getNewPin()));
        creditCard.setFirstLogin(false);
        creditCardRepository.save(creditCard);

        return new FirstLoginResponseDto("PIN generated successfully");
    }
}
