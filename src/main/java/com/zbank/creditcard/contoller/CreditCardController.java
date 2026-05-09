package com.zbank.creditcard.contoller;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.dto.response.ApplicantsResponseDto;
import com.zbank.creditcard.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credit")
@Slf4j
@Tag(name = "Credit Card Application", description = "APIs for applying for new credit cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/apply")
    @Operation(summary = "Submit a new card application", description = "Processes a new credit card application based on applicant details")
    public ResponseEntity<ApplicantsResponseDto> apply(@RequestBody ApplicantRequestDto applicantRequestDto) {
        log.info("Received credit card application request for: {} {}", applicantRequestDto.getFirstName(), applicantRequestDto.getLastName());
        ApplicantsResponseDto response = creditCardService.apply(applicantRequestDto);

        log.info("Successfully processed application request");
        return ResponseEntity.ok(response);
    }

}
