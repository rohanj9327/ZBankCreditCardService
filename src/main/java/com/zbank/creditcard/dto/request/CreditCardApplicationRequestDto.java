package com.zbank.creditcard.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardApplicationRequestDto {

    @NotNull(message = "Customer ID required")
    private Long customerId;

    @Valid
    @NotNull
    private IdentityDocumentRequestDto document;
}
