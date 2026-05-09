package com.zbank.creditcard.service.impl;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.dto.response.ApplicantsResponseDto;
import com.zbank.creditcard.entity.Applicants;
import com.zbank.creditcard.repository.ApplicantsRepository;
import com.zbank.creditcard.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final ApplicantsRepository applicantsRepository;

    @Override
    public ApplicantsResponseDto apply(ApplicantRequestDto applicantRequestDto) {

        if (applicantsRepository.existsByEmail(applicantRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Applicants applicant = Applicants.builder()
                .firstName(applicantRequestDto.getFirstName())
                .lastName(applicantRequestDto.getLastName())
                .email(applicantRequestDto.getEmail())
                .mobile(applicantRequestDto.getMobile())
                .annualSalary(applicantRequestDto.getAnnualSalary())
                .existingCreditCards(applicantRequestDto.getExistingCreditCards())
                .dob(applicantRequestDto.getDob())
                .createdAt(LocalDateTime.now())
                .build();


        return ApplicantsResponseDto.bu;
    }
}
