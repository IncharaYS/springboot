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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        UserEntity loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        UserEntity bookingCustomer;

        if (loggedInUser.getRole() == Role.ADMIN) {

            if (request.getCustomerId() == null) {
                throw new BusinessValidationException(
                        "Customer ID is required for admin booking");
            }

            bookingCustomer = userRepository.findByCustomerId(request.getCustomerId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Customer not found"));

        } else {
            bookingCustomer = loggedInUser;
        }

        if (request.getDropoffTime().isBefore(request.getPickupTime())) {
            throw new BusinessValidationException(
                    "Dropoff time cannot be before pickup time");
        }

        BigDecimal serviceCost = calculateServiceCost(
                request,
                loggedInUser.getRole() == Role.ADMIN
        );


        String bookingId = generateBookingId();

        BookingEntity booking = new BookingEntity();
        booking.setBookingId(bookingId);
        booking.setCustomer(bookingCustomer);
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
        booking.setBookedByAdmin(loggedInUser.getRole() == Role.ADMIN);
        booking.setBookingDate(LocalDateTime.now());

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

    private BigDecimal calculateServiceCost(BookingRequestDTO request, boolean isAdminBooking) {

        BigDecimal baseRate = BigDecimal.valueOf(50);

        BigDecimal weightCharge = BigDecimal.valueOf(
                request.getParcelWeight() * 0.02
        );

        BigDecimal deliveryCharge = switch (request.getDeliveryType()) {
            case STANDARD -> BigDecimal.valueOf(30);
            case EXPRESS -> BigDecimal.valueOf(80);
            case SAME_DAY -> BigDecimal.valueOf(150);
        };

        BigDecimal packingCharge = switch (request.getPackingPreference()) {
            case BASIC -> BigDecimal.valueOf(10);
            case PREMIUM -> BigDecimal.valueOf(30);
        };

        BigDecimal adminFee = isAdminBooking
                ? BigDecimal.valueOf(50)
                : BigDecimal.ZERO;

        BigDecimal subtotal = baseRate
                .add(weightCharge)
                .add(deliveryCharge)
                .add(packingCharge)
                .add(adminFee);

        return subtotal.multiply(BigDecimal.valueOf(1.05));
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

        return bookingRepository.findByCustomerEmailOrderByBookingDateDesc(email)
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
    public Page<BookingSummaryDTO> getAllBookingsPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("bookingDate").descending()
        );

        return bookingRepository.findAllByOrderByBookingDateDesc(pageable)
                .map(booking -> new BookingSummaryDTO(
                        booking.getBookingId(),
                        booking.getCustomer().getName(),
                        booking.getReceiverName(),
                        booking.getStatus(),
                        booking.getServiceCost()
                ));
    }


    @Override
    public void updatePickupDrop(PickupDropUpdateDTO request) {

        BookingEntity booking = bookingRepository.findByBookingId(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (request.getDropoffTime().isBefore(request.getPickupTime())) {
            throw new BusinessValidationException(
                    "Dropoff time cannot be before pickup time");
        }

        booking.setPickupTime(request.getPickupTime());
        booking.setDropoffTime(request.getDropoffTime());

        bookingRepository.save(booking);
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

        if (booking.getStatus() == BookingStatus.DELIVERED ||
                booking.getStatus() == BookingStatus.IN_TRANSIT) {

            throw new BusinessValidationException(
                    "Cannot cancel booking after parcel is in transit/delivered");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessValidationException(
                    "Booking already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }

    @Override
    public List<BookingSummaryDTO> filterBookings(BookingFilterDTO request) {

        return bookingRepository.filterBookings(
                        request.getBookingId(),
                        request.getCustomerId(),
                        request.getStatus()
                ).stream()
                .map(booking -> new BookingSummaryDTO(
                        booking.getBookingId(),
                        booking.getCustomer().getName(),
                        booking.getReceiverName(),
                        booking.getStatus(),
                        booking.getServiceCost()
                ))
                .toList();
    }


}