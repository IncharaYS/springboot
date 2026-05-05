package com.tcs.parcel_management.repository;

import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    boolean existsByBooking(BookingEntity booking);

    Optional<InvoiceEntity> findByBooking(BookingEntity booking);
}