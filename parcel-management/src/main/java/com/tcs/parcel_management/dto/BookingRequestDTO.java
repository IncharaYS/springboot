package com.tcs.parcel_management.dto;

import com.tcs.parcel_management.constants.DeliveryType;
import com.tcs.parcel_management.constants.PackingPreference;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingRequestDTO {

    private Integer customerId;

    @NotBlank(message = "Receiver name is required")
    private String receiverName;

    @NotBlank(message = "Receiver address is required")
    private String receiverAddress;

    @NotBlank(message = "Receiver PIN is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Enter valid 6-digit PIN")
    private String receiverPin;

    @NotBlank(message = "Receiver mobile is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Enter valid mobile number")
    private String receiverMobile;

    @NotNull(message = "Parcel weight is required")
    @Positive(message = "Parcel weight must be positive")
    private Double parcelWeight;

    @NotBlank(message = "Parcel contents are required")
    private String parcelContents;

    @NotNull(message = "Delivery type is required")
    private DeliveryType deliveryType;

    @NotNull(message = "Packing preference is required")
    private PackingPreference packingPreference;

    @NotNull(message = "Pickup time is required")
    private LocalDateTime pickupTime;

    @NotNull(message = "Dropoff time is required")
    private LocalDateTime dropoffTime;
}