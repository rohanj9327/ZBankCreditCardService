package com.zbank.creditcard.messaging.consumer;

import com.zbank.creditcard.constants.ApplicationConstants;
import com.zbank.creditcard.dto.request.RatingResultDto;
import com.zbank.creditcard.entity.ApplicationStatus;
import com.zbank.creditcard.repository.ApplicationStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRatingResultConsumer {

    private final ApplicationStatusRepository applicationStatusRepository;

    @KafkaListener(
            id = "zbank-customer-rating-result-consumer",
            topics = "customer-rating-results",
            groupId = "${zbank.kafka.consumer.rating-result.group-id}"
    )
    public void consumeRatingResult(@Payload RatingResultDto ratingResult) {
        log.info("Received rating result for Application ID: {}", ratingResult.applicationId());

        if (ApplicationConstants.PASSED.equalsIgnoreCase(ratingResult.status())) {

            ApplicationStatus status = applicationStatusRepository.findById(Long.valueOf(ratingResult.applicationId()))
                    .orElseThrow(() -> new RuntimeException("Applicant ID not found"));

            applicationStatusRepository.save(status);
            //TODO send notification succ
        } else {
            //TODO send notification succ
        }
    }

}