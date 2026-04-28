package com.tcs.parcel_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_fk", nullable = false, unique = true)
    private BookingEntity booking;

    @ManyToOne
    @JoinColumn(name = "customer_fk", nullable = false)
    private UserEntity customer;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDateTime feedbackDate;
}