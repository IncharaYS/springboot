package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.BookingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingStatusUpdateDTO {

    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    @NotNull(message = "Status is required")
    private BookingStatus status;
}