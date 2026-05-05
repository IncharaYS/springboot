package com.tcs.parcel_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PickupDropUpdateDTO {

    @NotBlank
    private String bookingId;

    @NotNull
    private LocalDateTime pickupTime;

    @NotNull
    private LocalDateTime dropoffTime;
}