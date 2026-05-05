package com.tcs.parcel_management.repository;

import com.tcs.parcel_management.constants.BookingStatus;
import com.tcs.parcel_management.entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    Optional<BookingEntity> findByBookingId(String bookingId);

    List<BookingEntity> findByCustomerEmailOrderByBookingDateDesc(String email);


    Page<BookingEntity> findAllByOrderByBookingDateDesc(Pageable pageable);


    @Query("""
    SELECT b FROM BookingEntity b
    WHERE (:bookingId IS NULL OR b.bookingId = :bookingId)
    AND (:customerId IS NULL OR b.customer.customerId = :customerId)
    AND (:status IS NULL OR b.status = :status)
    ORDER BY b.bookingDate DESC
""")
    List<BookingEntity> filterBookings(
            @Param("bookingId") String bookingId,
            @Param("customerId") Integer customerId,
            @Param("status") BookingStatus status
    );
}