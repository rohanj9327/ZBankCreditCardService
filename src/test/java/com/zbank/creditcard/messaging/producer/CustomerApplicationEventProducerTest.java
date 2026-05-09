package com.zbank.creditcard.messaging.producer;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import com.zbank.creditcard.messaging.event.CustomerApplicationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerApplicationEventProducerTest {

    @Mock
    private KafkaTemplate<String, CustomerApplicationEvent> kafkaTemplate;

    @InjectMocks
    private CustomerApplicationEventProducer producer;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(producer, "topicName", "test-topic");
    }

    @Test
    void shouldPublishEventWithCorrectData() {
        ApplicantRequestDto dto = ApplicantRequestDto.builder()
                .firstName("Bob")
                .lastName("Builder")
                .email("bob@zbank.com")
                .annualSalary(new BigDecimal("60000"))
                .build();

        producer.publishApplicationEvent(dto);

        ArgumentCaptor<CustomerApplicationEvent> eventCaptor = ArgumentCaptor.forClass(CustomerApplicationEvent.class);
        
        verify(kafkaTemplate).send(eq("test-topic"), anyString(), eventCaptor.capture());

        CustomerApplicationEvent capturedEvent = eventCaptor.getValue();
        
        assertEquals("CREDIT_SCORE_EVALUATION_STARTED", capturedEvent.eventType());
        assertEquals("Bob", capturedEvent.applicantData().getFirstName());
        assertEquals(new BigDecimal("60000"), capturedEvent.applicantData().getAnnualSalary());
    }
}