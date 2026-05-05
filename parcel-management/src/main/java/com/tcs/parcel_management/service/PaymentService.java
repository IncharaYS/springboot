package com.tcs.parcel_management.service;

import com.tcs.parcel_management.dto.PaymentRequestDTO;
import com.tcs.parcel_management.dto.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO processPayment(PaymentRequestDTO request);
}
