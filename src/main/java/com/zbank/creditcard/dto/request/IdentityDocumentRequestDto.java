package com.zbank.creditcard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentityDocumentRequestDto {

    @NotBlank(message = "Document type required")
    private String documentType;

    @NotBlank(message = "Document number required")
    @Size(min = 5, max = 20)
    private String documentNumber;
}
