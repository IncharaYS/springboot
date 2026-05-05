package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BookingResponseDTO {

    private String bookingId;
    private BigDecimal serviceCost;
    private BookingStatus status;
    private String message;
}
