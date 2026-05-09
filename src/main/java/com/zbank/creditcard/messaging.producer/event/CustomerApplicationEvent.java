package com.zbank.creditcard.messaging.producer.event;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import lombok.Builder;

@Builder
public record CustomerApplicationEvent(String applicationId,
                                       String eventType,
                                       ApplicantRequestDto applicantData
) {
}