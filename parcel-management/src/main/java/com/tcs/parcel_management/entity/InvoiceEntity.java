package com.tcs.parcel_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "invoices")
public class InvoiceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", unique = true, nullable = false)
    private String invoiceNumber;

    @OneToOne
    @JoinColumn(name = "booking_fk", nullable = false, unique = true)
    private BookingEntity booking;

    @OneToOne
    @JoinColumn(name = "payment_fk", nullable = false, unique = true)
    private PaymentEntity payment;

    @Column(nullable = false)
    private LocalDateTime generatedAt;
}