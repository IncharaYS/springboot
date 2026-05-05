package com.tcs.parcel_management.entity;

import com.tcs.parcel_management.constants.BookingStatus;
import com.tcs.parcel_management.constants.DeliveryType;
import com.tcs.parcel_management.constants.PackingPreference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class BookingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", unique = true, nullable = false)
    private String bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id_fk", nullable = false)
    private UserEntity customer;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String receiverAddress;

    @Column(nullable = false)
    private String receiverPin;

    @Column(nullable = false)
    private String receiverMobile;

    @Column(nullable = false)
    private Double parcelWeight;

    @Column(nullable = false)
    private String parcelContents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackingPreference packingPreference;

    private LocalDateTime pickupTime;

    private LocalDateTime dropoffTime;

    @Column(nullable = false)
    private BigDecimal serviceCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Boolean bookedByAdmin = false;

    @Column(nullable = false)
    private LocalDateTime bookingDate;


}
