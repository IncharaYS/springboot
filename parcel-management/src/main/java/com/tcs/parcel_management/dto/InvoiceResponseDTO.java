package com.tcs.parcel_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvoiceResponseDTO {

    private String invoiceNumber;
    private String message;
}