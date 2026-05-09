package com.zbank.creditcard.messaging.event;

import com.zbank.creditcard.dto.request.ApplicantRequestDto;
import lombok.Builder;

@Builder
public record CustomerApplicationEvent(Long applicationId, String eventType, ApplicantRequestDto applicantData) {

}