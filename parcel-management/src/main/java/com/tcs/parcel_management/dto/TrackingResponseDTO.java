package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TrackingResponseDTO {

    private String bookingId;
    private BookingStatus status;
    private String receiverName;
    private String receiverAddress;
    private BigDecimal serviceCost;
    private LocalDateTime pickupTime;
    private LocalDateTime dropoffTime;
}