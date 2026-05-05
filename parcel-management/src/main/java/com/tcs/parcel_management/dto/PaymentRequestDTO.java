package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.CardType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {

    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    @NotNull(message = "Card type is required")
    private CardType cardType;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "^\\d{16}$", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotBlank(message = "Card holder name is required")
    private String cardHolderName;

    @NotNull(message = "Expiry month is required")
    @Min(value = 1)
    @Max(value = 12)
    private Integer expiryMonth;

    @NotNull(message = "Expiry year is required")
    private Integer expiryYear;

    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "^\\d{3,4}$", message = "Invalid CVV")
    private String cvv;
}