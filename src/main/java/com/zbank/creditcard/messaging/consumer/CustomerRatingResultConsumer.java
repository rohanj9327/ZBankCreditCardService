package com.zbank.creditcard.messaging.consumer;

import com.zbank.creditcard.constants.ApplicationConstants;
import com.zbank.creditcard.dto.request.RatingResultDto;
import com.zbank.creditcard.entity.ApplicationStatus;
import com.zbank.creditcard.messaging.event.NotificationEvent;
import com.zbank.creditcard.messaging.producer.NotificationProducer;
import com.zbank.creditcard.repository.ApplicationStatusRepository;
import com.zbank.creditcard.service.CreditCardCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerRatingResultConsumer {

    private final ApplicationStatusRepository
            applicationStatusRepository;

    private final CreditCardCreationService
            creditCardCreationService;

    private final NotificationProducer notificationProducer;

    @KafkaListener(
            id = "zbank-customer-rating-result-consumer",
            topics = "customer-rating-results",
            groupId =
                    "${zbank.kafka.consumer.rating-result.group-id}"
    )
    public void consumeRatingResult(
            @Payload RatingResultDto ratingResult
    ) {

        log.info(
                "Received rating result for Application ID: {}",
                ratingResult.applicationId()
        );

        ApplicationStatus status =
                applicationStatusRepository
                        .findByApplicantsId(
                                Long.valueOf(
                                        ratingResult.applicationId()
                                )
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Applicant ID not found"
                                )
                        );

        /*
            Save score data
         */
        status.setCreditScore(
                ratingResult.score()
        );

        status.setCardType(
                ratingResult.cardType()
        );

        /*
            APPROVED
         */
        if (ApplicationConstants.PASSED
                .equalsIgnoreCase(
                        ratingResult.status()
                )) {

            status.setStatus(
                    "APPROVED"
            );

            applicationStatusRepository.save(
                    status
            );

            /*
                CREATE CREDIT CARD
             */
            creditCardCreationService
                    .createCard(status);

            log.info(
                    "Credit card created for applicationId={}",
                    ratingResult.applicationId()
            );

            notificationProducer.sendNotification(NotificationEvent.builder()
                    .messageBody("Credit card approved against application id" + status.getId())
                    .customerEmail("test.rohan@gmail.com").build());
        }

        /*
            REJECTED
         */
        else {

            status.setStatus(
                    "REJECTED"
            );

            status.setFailureReason(
                    ratingResult.failureReason()
            );

            applicationStatusRepository.save(
                    status
            );

            log.info(
                    "Application rejected for applicationId={}",
                    ratingResult.applicationId()
            );

            notificationProducer.sendNotification(NotificationEvent.builder()
                    .messageBody("Credit card rejected against application id" + status.getId())
                    .customerEmail("test.rohan@gmail.com").build());
        }
    }
}