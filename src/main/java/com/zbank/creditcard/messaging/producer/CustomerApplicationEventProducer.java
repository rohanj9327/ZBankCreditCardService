package com.zbank.creditcard.messaging.producer;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.entity.Applicants;
import com.zbank.creditcard.messaging.event.CustomerApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerApplicationEventProducer {

    // Change the Template type to <String, String>
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${zbank.kafka.topic.customer-application:customerapplicationevent}")
    private String topicName;

    public void publishApplicationEvent(ApplicantRequestDto applicantDto, Applicants updated) {
        try {
            CustomerApplicationEvent event = CustomerApplicationEvent.builder()
                    .applicationId(updated.getId())
                    .eventType("CREDIT_SCORE_EVALUATION_STARTED")
                    .applicantData(applicantDto)
                    .build();

            // Manually convert Object to JSON String
            String jsonMessage = objectMapper.writeValueAsString(event);

            log.info("Publishing JSON to topic {}: {}", topicName, jsonMessage);

            // Send as String
            kafkaTemplate.send(topicName, String.valueOf(event.getApplicationId()), jsonMessage);

        } catch (Exception e) {
            log.error("Serialization failed: {}", e.getMessage());
        }
    }
}