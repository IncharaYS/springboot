package com.tcs.parcel_management.service.impl;

import com.tcs.parcel_management.constants.BookingStatus;
import com.tcs.parcel_management.constants.Role;
import com.tcs.parcel_management.dto.*;
import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.UserEntity;
import com.tcs.parcel_management.exception.BusinessValidationException;
import com.tcs.parcel_management.exception.ResourceNotFoundException;
import com.tcs.parcel_management.repository.BookingRepository;
import com.tcs.parcel_management.repository.FeedbackRepository;
import com.tcs.parcel_management.repository.UserRepository;
import com.tcs.parcel_management.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;


    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO request) {


        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserEntity customer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new BusinessValidationException("Only customers can create bookings");
        }

        String bookingId = generateBookingId();

        BigDecimal serviceCost = calculateServiceCost(request);

        BookingEntity booking = new BookingEntity();
        booking.setBookingId(bookingId);
        booking.setCustomer(customer);
        booking.setBookingDate(LocalDateTime.now());
        booking.setReceiverName(request.getReceiverName());
        booking.setReceiverAddress(request.getReceiverAddress());
        booking.setReceiverPin(request.getReceiverPin());
        booking.setReceiverMobile(request.getReceiverMobile());
        booking.setParcelWeight(request.getParcelWeight());
        booking.setParcelContents(request.getParcelContents());
        booking.setDeliveryType(request.getDeliveryType());
        booking.setPackingPreference(request.getPackingPreference());
        booking.setPickupTime(request.getPickupTime());
        booking.setDropoffTime(request.getDropoffTime());
        booking.setServiceCost(serviceCost);
        booking.setStatus(BookingStatus.BOOKED);
        booking.setBookedByAdmin(false);

        if (request.getDropoffTime().isBefore(request.getPickupTime())) {
            throw new BusinessValidationException(
                    "Dropoff time cannot be before pickup time");
        }

        bookingRepository.save(booking);

        return new BookingResponseDTO(
                bookingId,
                serviceCost,
                booking.getStatus(),
                "Booking created successfully"
        );
    }

    private String generateBookingId() {
        long count = bookingRepository.count();
        return "BID" + (5001 + count);
    }

    private BigDecimal calculateServiceCost(BookingRequestDTO request) {

        BigDecimal cost = BigDecimal.valueOf(request.getParcelWeight() * 50);

        switch (request.getDeliveryType()) {
            case EXPRESS -> cost = cost.add(BigDecimal.valueOf(100));
            case SAME_DAY -> cost = cost.add(BigDecimal.valueOf(200));
        }

        switch (request.getPackingPreference()) {
            case PREMIUM -> cost = cost.add(BigDecimal.valueOf(75));
        }

        return cost;
    }


    @Override
    public TrackingResponseDTO trackBooking(String bookingId) {

        BookingEntity booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        return new TrackingResponseDTO(
                booking.getBookingId(),
                booking.getStatus(),
                booking.getReceiverName(),
                booking.getReceiverAddress(),
                booking.getServiceCost(),
                booking.getPickupTime(),
                booking.getDropoffTime()
        );
    }


    @Override
    public List<BookingSummaryDTO> getMyBookings() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return bookingRepository
                .findByCustomerEmailOrderByBookingDateDesc(email)
                .stream()
                .map(booking -> new BookingSummaryDTO(
                        booking.getBookingId(),
                        booking.getCustomer().getName(),
                        booking.getReceiverName(),
                        booking.getStatus(),
                        booking.getServiceCost()
                ))
                .toList();
    }

    @Override
    public void updateBookingStatus(BookingStatusUpdateDTO request) {

        BookingEntity booking = bookingRepository.findByBookingId(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        booking.setStatus(request.getStatus());

        bookingRepository.save(booking);
    }


    @Override
    public List<BookingSummaryDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(booking -> new BookingSummaryDTO(
                        booking.getBookingId(),
                        booking.getCustomer().getName(),
                        booking.getReceiverName(),
                        booking.getStatus(),
                        booking.getServiceCost()
                ))
                .toList();
    }


    @Override
    public void cancelBooking(String bookingId) {

        BookingEntity booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() != BookingStatus.BOOKED) {
            throw new BusinessValidationException(
                    "Booking can only be cancelled before shipment");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }


}