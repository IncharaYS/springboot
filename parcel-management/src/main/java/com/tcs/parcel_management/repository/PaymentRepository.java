package com.tcs.parcel_management.repository;

import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    boolean existsByBooking(BookingEntity booking);

    Optional<PaymentEntity> findByBooking(BookingEntity booking);
}