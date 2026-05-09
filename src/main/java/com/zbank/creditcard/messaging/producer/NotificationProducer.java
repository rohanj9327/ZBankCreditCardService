package com.zbank.creditcard.messaging.producer;

import com.zbank.creditcard.messaging.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    
    private static final String TOPIC = "zbank-notification";

    public void sendNotification(NotificationEvent event) {
        log.info(">>> PRODUCING: Sending notification to Kafka for AppID: {}", event.getApplicationId());
        
        try {
            kafkaTemplate.send(TOPIC, event.getApplicationId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("<<< PRODUCED SUCCESS: Message sent to topic [{}] at offset {}", 
                                TOPIC, result.getRecordMetadata().offset());
                    } else {
                        log.error("!!! PRODUCED FAILURE: Unable to send message. Error: {}", ex.getMessage());
                    }
                });
        } catch (Exception e) {
            log.error("CRITICAL PRODUCER ERROR: {}", e.getMessage());
        }
    }
}