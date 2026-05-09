package com.zbank.creditcard.service.impl;

import com.zbank.creditcard.constants.ApplicationConstants;
import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.entity.Applicants;
import com.zbank.creditcard.entity.ApplicationStatus;
import com.zbank.creditcard.messaging.producer.CustomerApplicationEventProducer;
import com.zbank.creditcard.repository.ApplicantsRepository;
import com.zbank.creditcard.repository.ApplicationStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreditCardServiceImplTest {

    @Mock
    private ApplicantsRepository applicantsRepository;

    @Mock
    private ApplicationStatusRepository applicationStatusRepository;

    @Mock
    private CustomerApplicationEventProducer producer;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldApplyCreditCardSuccessfully() {

        ApplicantRequestDto dto = new ApplicantRequestDto();

        dto.setFirstName("Nandish");
        dto.setLastName("Bhuva");
        dto.setEmail("nandish@gmail.com");
        dto.setMobile("9876543210");
        dto.setAnnualSalary(new BigDecimal("250000") );
        dto.setExistingCreditCards(new int[] { 1, 2});
        dto.setEmploymentType("SALARIED");
        dto.setDob(LocalDate.of(2000, 5, 10));

        Applicants savedApplicant = Applicants.builder()
                        .id(1L)
                        .firstName("Nandish")
                        .lastName("Bhuva")
                        .email("nandish@gmail.com")
                        .build();

        when(applicantsRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        when(applicantsRepository.save(any(Applicants.class))).thenReturn(savedApplicant);

        //When
        creditCardService.apply(dto);

        // THEN
        verify(applicantsRepository,times(1)).save(any(Applicants.class));

        verify(applicationStatusRepository,times(1)).save(any(ApplicationStatus.class));

        verify(producer,times(1)).publishApplicationEvent(any(),any());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        // GIVEN
        ApplicantRequestDto dto = new ApplicantRequestDto();
        dto.setEmail("nandish@gmail.com");
        when(applicantsRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        // WHEN + THEN
        RuntimeException exception = assertThrows(RuntimeException.class,() -> creditCardService.apply(dto));

        assertEquals("Email already exists",exception.getMessage());

        verify(applicantsRepository,never()).save(any());

        verify(producer,never()).publishApplicationEvent(any(),any());
    }
}