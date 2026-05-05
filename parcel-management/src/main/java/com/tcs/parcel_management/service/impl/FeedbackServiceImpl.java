package com.tcs.parcel_management.service.impl;

import com.tcs.parcel_management.constants.BookingStatus;
import com.tcs.parcel_management.dto.FeedbackRequestDTO;
import com.tcs.parcel_management.dto.FeedbackResponseDTO;
import com.tcs.parcel_management.dto.FeedbackViewDTO;
import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.FeedbackEntity;
import com.tcs.parcel_management.exception.BusinessValidationException;
import com.tcs.parcel_management.exception.ResourceNotFoundException;
import com.tcs.parcel_management.repository.BookingRepository;
import com.tcs.parcel_management.repository.FeedbackRepository;
import com.tcs.parcel_management.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    @Override
    public FeedbackResponseDTO submitFeedback(FeedbackRequestDTO request) {

        BookingEntity booking = bookingRepository.findByBookingId(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() != BookingStatus.DELIVERED) {
            throw new BusinessValidationException(
                    "Feedback can only be submitted after delivery");
        }

        if (feedbackRepository.existsByBooking(booking)) {
            throw new BusinessValidationException(
                    "Feedback already submitted for this booking");
        }

        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setBooking(booking);
        feedback.setCustomer(booking.getCustomer());
        feedback.setRating(request.getRating());
        feedback.setDescription(request.getComments());
        feedback.setFeedbackDate(LocalDateTime.now());

        feedbackRepository.save(feedback);

        return new FeedbackResponseDTO(
                "Feedback submitted successfully"
        );
    }


    @Override
    public List<FeedbackViewDTO> getAllFeedback() {

        return feedbackRepository.findAll()
                .stream()
                .map(feedback -> new FeedbackViewDTO(
                        feedback.getBooking().getBookingId(),
                        feedback.getCustomer().getName(),
                        feedback.getRating(),
                        feedback.getDescription()
                ))
                .toList();
    }
}