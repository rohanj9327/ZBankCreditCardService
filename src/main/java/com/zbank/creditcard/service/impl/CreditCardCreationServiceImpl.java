package com.zbank.creditcard.service.impl;

import com.zbank.creditcard.entity.ApplicationStatus;
import com.zbank.creditcard.entity.CreditCard;
import com.zbank.creditcard.repository.CreditCardRepository;
import com.zbank.creditcard.service.CreditCardCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardCreationServiceImpl implements CreditCardCreationService {

    private final CreditCardRepository
            creditCardRepository;

    public void createCard(
            ApplicationStatus status
    ) {

        CreditCard card =
                CreditCard.builder()

                        .cardNumber(
                                generateCardNumber()
                        )

                        .cardType(
                                status.getCardType()
                        )

                        .cardLimit(
                                determineLimit(
                                        status.getCardType()
                                )
                        )

                        .encryptedPin(
                                "TEMP_PIN"
                        )

                        .firstLogin(true)

                        .build();

        creditCardRepository.save(card);
    }

    private String generateCardNumber() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 16);
    }

    private BigDecimal determineLimit(
            String cardType
    ) {

        return switch (cardType) {

            case "PLATINUM" ->
                    BigDecimal.valueOf(1000000);

            case "GOLD" ->
                    BigDecimal.valueOf(500000);

            default ->
                    BigDecimal.valueOf(100000);
        };
    }
}