package com.tcs.parcel_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackViewDTO {

    private String bookingId;
    private String customerName;
    private Integer rating;
    private String description;
}