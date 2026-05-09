package com.zbank.creditcard.messaging.consumer;

import com.zbank.creditcard.constants.ApplicationConstants;
import com.zbank.creditcard.dto.request.RatingResultDto;
import com.zbank.creditcard.entity.ApplicationStatus;
import com.zbank.creditcard.messaging.event.NotificationEvent;
import com.zbank.creditcard.messaging.producer.NotificationProducer;
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

    private final NotificationProducer notificationProducer;

    @KafkaListener(
            id = "zbank-customer-rating-result-consumer",
            topics = "customer-rating-results",
            groupId = "${zbank.kafka.consumer.rating-result.group-id}"
    )
    public void consumeRatingResult(@Payload RatingResultDto ratingResult) {
        log.info("Received rating result for Application ID: {}", ratingResult.applicationId());


        ApplicationStatus status = applicationStatusRepository.findById(Long.valueOf(ratingResult.applicationId()))
                .orElseThrow(() -> new RuntimeException("Applicant ID not found"));

        if (ApplicationConstants.PASSED.equalsIgnoreCase(ratingResult.status())) {

            applicationStatusRepository.save(status);
            notificationProducer.sendNotification(NotificationEvent.builder()
                            .messageBody("Credit card approved against application id" + status.getId())
                    .customerEmail("test.rohan@gmail.com").build());
        } else {
            notificationProducer.sendNotification(NotificationEvent.builder()
                    .messageBody("Credit card rejected against application id" + status.getId())
                    .customerEmail("test.rohan@gmail.com").build());
        }
    }

}