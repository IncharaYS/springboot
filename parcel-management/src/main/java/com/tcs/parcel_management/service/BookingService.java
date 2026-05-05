package com.tcs.parcel_management.service;

import com.tcs.parcel_management.dto.*;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO request);

    TrackingResponseDTO trackBooking(String bookingId);

    void updateBookingStatus(BookingStatusUpdateDTO request);

    List<BookingSummaryDTO> getAllBookings();

    void cancelBooking(String bookingId);

    List<BookingSummaryDTO> getMyBookings();
}