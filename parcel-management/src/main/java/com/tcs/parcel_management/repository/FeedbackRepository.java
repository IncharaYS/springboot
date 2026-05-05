package com.tcs.parcel_management.repository;

import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    boolean existsByBooking(BookingEntity booking);
}