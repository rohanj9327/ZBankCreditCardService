package com.zbank.creditcard.service;

import com.zbank.creditcard.dto.request.FirstLoginRequestDto;
import com.zbank.creditcard.dto.response.FirstLoginResponseDto;
import com.zbank.creditcard.entity.CreditCard;
import com.zbank.creditcard.repository.CreditCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirstLoginServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private FirstLoginService firstLoginService;

    @Test
    void generatePinUpdatesPinForValidFirstLogin() {
        CreditCard creditCard = creditCard(true);
        FirstLoginRequestDto request = validRequest();

        when(creditCardRepository.findByCardNumber("4111111111111111")).thenReturn(Optional.of(creditCard));
        when(passwordEncoder.matches("1234", "encoded-default-pin")).thenReturn(true);
        when(passwordEncoder.encode("4321")).thenReturn("encoded-new-pin");

        FirstLoginResponseDto response = firstLoginService.generatePin(request);

        assertThat(response.getMessage()).isEqualTo("PIN generated successfully");
        assertThat(creditCard.getEncryptedPin()).isEqualTo("encoded-new-pin");
        assertThat(creditCard.getFirstLogin()).isFalse();
        verify(creditCardRepository).save(creditCard);
    }

    @Test
    void generatePinRejectsMismatchedNewPinConfirmation() {
        FirstLoginRequestDto request = validRequest();
        request.setConfirmPin("9999");

        assertThatThrownBy(() -> firstLoginService.generatePin(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("New PIN and confirm PIN do not match");

        verify(creditCardRepository, never()).save(any());
    }

    @Test
    void generatePinRejectsInvalidFirstTimePin() {
        CreditCard creditCard = creditCard(true);
        FirstLoginRequestDto request = validRequest();

        when(creditCardRepository.findByCardNumber("4111111111111111")).thenReturn(Optional.of(creditCard));
        when(passwordEncoder.matches("1234", "encoded-default-pin")).thenReturn(false);

        assertThatThrownBy(() -> firstLoginService.generatePin(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid first time PIN");

        verify(creditCardRepository, never()).save(any());
    }

    @Test
    void generatePinRejectsCompletedFirstLogin() {
        CreditCard creditCard = creditCard(false);
        FirstLoginRequestDto request = validRequest();

        when(creditCardRepository.findByCardNumber("4111111111111111")).thenReturn(Optional.of(creditCard));

        assertThatThrownBy(() -> firstLoginService.generatePin(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("First time login already completed");

        verify(creditCardRepository, never()).save(any());
    }

    private FirstLoginRequestDto validRequest() {
        FirstLoginRequestDto request = new FirstLoginRequestDto();
        request.setCardNumber("4111111111111111");
        request.setFirstTimePin("1234");
        request.setNewPin("4321");
        request.setConfirmPin("4321");
        return request;
    }

    private CreditCard creditCard(boolean firstLogin) {
        return CreditCard.builder()
                .cardNumber("4111111111111111")
                .encryptedPin("encoded-default-pin")
                .firstLogin(firstLogin)
                .build();
    }
}
