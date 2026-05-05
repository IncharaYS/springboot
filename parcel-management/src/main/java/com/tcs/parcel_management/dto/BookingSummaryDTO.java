package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BookingSummaryDTO {

    private String bookingId;
    private String customerName;
    private String receiverName;
    private BookingStatus status;
    private BigDecimal serviceCost;
}