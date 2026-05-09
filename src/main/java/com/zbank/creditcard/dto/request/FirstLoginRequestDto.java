package com.zbank.creditcard.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstLoginRequestDto {

    @NotBlank
    @Schema(example = "1234567890123456", description = "The 16-digit credit card number")
    private String cardNumber;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "PIN must be 4 digits"
    )
    @Schema(example = "1234", description = "The 4 digit pin for first time use")
    private String firstTimePin;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "New PIN must be 4 digits"
    )
    @Schema(example = "1234", description = "The 4 digit pin after user sets explicitly")
    private String newPin;

    @NotBlank
    @Pattern(
            regexp = "^[0-9]{4}$",
            message = "Confirm PIN must be 4 digits"
    )
    @Schema(example = "1234", description = "The 4 digit pin matching with thw new pin")
    private String confirmPin;
}
