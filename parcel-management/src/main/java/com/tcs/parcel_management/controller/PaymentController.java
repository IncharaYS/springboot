package com.tcs.parcel_management.controller;

import com.tcs.parcel_management.dto.PaymentRequestDTO;
import com.tcs.parcel_management.dto.PaymentResponseDTO;
import com.tcs.parcel_management.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @Valid @RequestBody PaymentRequestDTO request) {

        return ResponseEntity.ok(
                paymentService.processPayment(request)
        );
    }
}