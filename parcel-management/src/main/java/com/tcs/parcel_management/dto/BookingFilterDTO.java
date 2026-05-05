package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.BookingStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingFilterDTO {

    private String bookingId;
    private Integer customerId;
    private BookingStatus status;
}