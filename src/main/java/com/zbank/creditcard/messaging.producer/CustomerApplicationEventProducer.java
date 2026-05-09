package com.zbank.creditcard.messaging.producer;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.messaging.producer.event.CustomerApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerApplicationEventProducer {

    private final KafkaTemplate<String, CustomerApplicationEvent> kafkaTemplate;

    @Value("${zbank.kafka.topic.customer-application:customerapplicationevent}")
    private String topicName;

    public void publishApplicationEvent(ApplicantRequestDto applicantDto) {
        CustomerApplicationEvent event = CustomerApplicationEvent.builder()
                .applicationId(applicantDto.getEmail())
                .eventType("CREDIT_SCORE_EVALUATION_STARTED")
                .applicantData(applicantDto)
                .build();

        log.info("Publishing event to topic {}: applicationId={}", topicName, event.applicationId());

        kafkaTemplate.send(topicName, event.applicationId(), event);
    }
}