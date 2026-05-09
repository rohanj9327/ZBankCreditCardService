package com.zbank.creditcard.dto.request;

import lombok.Builder;

@Builder
public record RatingResultDto(
    String status,
    Integer score,
    String cardType,
    String applicationId,
    String failureReason
) {}