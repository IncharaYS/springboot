package com.tcs.parcel_management.entity;

import com.tcs.parcel_management.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", unique = true, nullable = false)
    private String paymentId;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @OneToOne
    @JoinColumn(name = "booking_fk", nullable = false, unique = true)
    private BookingEntity booking;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private LocalDateTime paymentDate;
}