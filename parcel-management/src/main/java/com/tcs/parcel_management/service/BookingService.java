package com.tcs.parcel_management.service;

import com.tcs.parcel_management.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO request);

    TrackingResponseDTO trackBooking(String bookingId);

    void updateBookingStatus(BookingStatusUpdateDTO request);

    List<BookingSummaryDTO> getAllBookings();

    void cancelBooking(String bookingId);

    List<BookingSummaryDTO> getMyBookings();


    void updatePickupDrop(PickupDropUpdateDTO request);

    Page<BookingSummaryDTO> getAllBookingsPaginated(int page, int size);

    List<BookingSummaryDTO> filterBookings(BookingFilterDTO request);
}