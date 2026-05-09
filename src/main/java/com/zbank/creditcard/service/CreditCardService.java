package com.zbank.creditcard.service;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.dto.response.ApplicantsResponseDto;

public interface CreditCardService {
    ApplicantsResponseDto apply(ApplicantRequestDto applicantRequestDto);
}
