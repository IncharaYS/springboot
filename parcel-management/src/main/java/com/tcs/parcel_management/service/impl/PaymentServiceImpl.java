package com.tcs.parcel_management.service.impl;

import com.tcs.parcel_management.constants.BookingStatus;
import com.tcs.parcel_management.constants.PaymentStatus;
import com.tcs.parcel_management.dto.PaymentRequestDTO;
import com.tcs.parcel_management.dto.PaymentResponseDTO;
import com.tcs.parcel_management.entity.BookingEntity;
import com.tcs.parcel_management.entity.CardEntity;
import com.tcs.parcel_management.entity.PaymentEntity;
import com.tcs.parcel_management.exception.BusinessValidationException;
import com.tcs.parcel_management.exception.ResourceNotFoundException;
import com.tcs.parcel_management.repository.BookingRepository;
import com.tcs.parcel_management.repository.CardRepository;
import com.tcs.parcel_management.repository.PaymentRepository;
import com.tcs.parcel_management.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final CardRepository cardRepository;

    @Override
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {

        BookingEntity booking = bookingRepository.findByBookingId(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Booking not found"));

        if (paymentRepository.existsByBooking(booking)) {
            throw new BusinessValidationException(
                    "Payment already completed for this booking");
        }

        CardEntity card = cardRepository
                .findByCardNumberAndCvvAndExpiryMonthAndExpiryYearAndActiveTrue(
                        request.getCardNumber(),
                        request.getCvv(),
                        request.getExpiryMonth(),
                        request.getExpiryYear()
                )
                .orElseThrow(() ->
                        new BusinessValidationException("Invalid card details"));


        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessValidationException(
                    "Cannot make payment for cancelled booking");
        }

        if (card.getCardType() != request.getCardType()) {
            throw new BusinessValidationException("Card type mismatch");
        }

        if (!card.getCardHolderName().equalsIgnoreCase(request.getCardHolderName())) {
            throw new BusinessValidationException("Card holder name mismatch");
        }

        String paymentId = generatePaymentId();
        String transactionId = generateTransactionId();

        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(paymentId);
        payment.setTransactionId(transactionId);
        payment.setBooking(booking);
        payment.setAmount(booking.getServiceCost());
        payment.setPaymentType(request.getCardType().name());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);

        return new PaymentResponseDTO(
                paymentId,
                transactionId,
                payment.getAmount(),
                payment.getPaymentStatus(),
                "Payment processed successfully"
        );
    }

    private String generatePaymentId() {
        return "PAY" + (3001 + paymentRepository.count());
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}