package com.tcs.parcel_management.controller;

import com.tcs.parcel_management.dto.*;
import com.tcs.parcel_management.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;



    @GetMapping("/paged")
    public ResponseEntity<Page<BookingSummaryDTO>> getAllBookingsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                bookingService.getAllBookingsPaginated(page, size)
        );
    }

    @PutMapping("/pickup-drop")
    public ResponseEntity<String> updatePickupDrop(
            @Valid @RequestBody PickupDropUpdateDTO request) {

        bookingService.updatePickupDrop(request);

        return ResponseEntity.ok("Pickup and drop time updated successfully");
    }



    @GetMapping("/my")
    public ResponseEntity<List<BookingSummaryDTO>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings()
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<TrackingResponseDTO> trackBooking(
            @PathVariable String bookingId) {

        return ResponseEntity.ok(
                bookingService.trackBooking(bookingId)
        );
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateBookingStatus(
            @Valid @RequestBody BookingStatusUpdateDTO request) {

        bookingService.updateBookingStatus(request);

        return ResponseEntity.ok("Booking status updated successfully");
    }


    @GetMapping
    public ResponseEntity<List<BookingSummaryDTO>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }


    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable String bookingId) {

        bookingService.cancelBooking(bookingId);

        return ResponseEntity.ok("Booking cancelled successfully");
    }




    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO request) {

        return ResponseEntity.ok(
                bookingService.createBooking(request)
        );
    }

    @PostMapping("/filter")
    public ResponseEntity<List<BookingSummaryDTO>> filterBookings(
            @RequestBody BookingFilterDTO request) {

        return ResponseEntity.ok(
                bookingService.filterBookings(request)
        );
    }
}