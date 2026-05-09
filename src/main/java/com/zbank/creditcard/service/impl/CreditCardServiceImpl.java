package com.zbank.creditcard.service.impl;

import com.zbank.creditcard.constants.ApplicationConstants;
import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.dto.response.ApplicantsResponseDto;
import com.zbank.creditcard.entity.Applicants;
import com.zbank.creditcard.entity.ApplicationStatus;
import com.zbank.creditcard.messaging.producer.CustomerApplicationEventProducer;
import com.zbank.creditcard.repository.ApplicantsRepository;
import com.zbank.creditcard.repository.ApplicationStatusRepository;
import com.zbank.creditcard.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final ApplicantsRepository applicantsRepository;

    private final ApplicationStatusRepository creditStatusRepository;

    private final CustomerApplicationEventProducer customerApplicationEventProducer;

    @Override
    public ApplicantsResponseDto apply(ApplicantRequestDto applicantRequestDto) {

        validateEmail(applicantRequestDto.getEmail());

        Applicants applicant = Applicants.builder()
                .firstName(applicantRequestDto.getFirstName())
                .lastName(applicantRequestDto.getLastName())
                .email(applicantRequestDto.getEmail())
                .mobile(applicantRequestDto.getMobile())
                .annualSalary(applicantRequestDto.getAnnualSalary())
                .existingCreditCards(applicantRequestDto.getExistingCreditCards())
                .dob(applicantRequestDto.getDob())
                .employmentType(applicantRequestDto.getEmploymentType())
                .aadharNumber(applicantRequestDto.getAadharNumber())
                .panNumber(applicantRequestDto.getPanNumber())
                .createdAt(LocalDateTime.now())
                .build();

        Applicants savedApplicant = applicantsRepository.save(applicant);

        ApplicationStatus creditCardApplicationStatus = ApplicationStatus.builder()
                .status(ApplicationConstants.PENDING)
                .applicants(savedApplicant)
                .build();

        creditStatusRepository.save(creditCardApplicationStatus);

        customerApplicationEventProducer.publishApplicationEvent(applicantRequestDto, savedApplicant);

        String successMessage = String.format("Credit Card application submitted successfully! Your application ID is: %d",savedApplicant.getId());

        return ApplicantsResponseDto.builder()
                .message(successMessage)
                .build();
    }

    private void validateEmail(String email) {
        if (applicantsRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
    }
}
