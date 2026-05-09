package com.zbank.creditcard.messaging.consumer;

import com.zbank.creditcard.dto.request.RatingResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRatingResultConsumer {
    @KafkaListener(
            id = "zbank-customer-rating-result-consumer",
            topics = "customer-rating-results",
            groupId = "${zbank.kafka.consumer.rating-result.group-id}"
    )
    public void consumeRatingResult(@Payload RatingResultDto ratingResult) {
        log.info("Received rating result for Application ID: {}", ratingResult.applicationId());

        if ("Passed".equalsIgnoreCase(ratingResult.status())) {
            //todo update status
            //TODO send notification succ
        } else {
            //TODO send notification succ
        }
    }

}