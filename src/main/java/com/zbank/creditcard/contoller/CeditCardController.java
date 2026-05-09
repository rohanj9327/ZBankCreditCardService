package com.zbank.creditcard.contoller;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.dto.response.ApplicantsResponseDto;
import com.zbank.creditcard.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CeditCardController {

    private final CreditCardService creditCardService;

    @PostMapping
    public ResponseEntity<ApplicantsResponseDto> apply(@RequestBody ApplicantRequestDto applicantRequestDto) {
        return ResponseEntity.ok(creditCardService.apply(applicantRequestDto));
    }
}
