package com.zbank.creditcard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstLoginRequestDto {

    @NotBlank
    private String cardNumber;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "PIN must be 4 digits"
    )
    private String firstTimePin;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "New PIN must be 4 digits"
    )
    private String newPin;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "Confirm PIN must be 4 digits"
    )
    private String confirmPin;
}
