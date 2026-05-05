package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentResponseDTO {

    private String paymentId;
    private String transactionId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private String message;
}