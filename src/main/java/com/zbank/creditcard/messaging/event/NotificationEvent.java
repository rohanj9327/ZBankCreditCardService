package com.zbank.creditcard.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String applicationId;
    private String customerEmail;
    private String status;
    private String messageBody;
    private String failureReason;
}