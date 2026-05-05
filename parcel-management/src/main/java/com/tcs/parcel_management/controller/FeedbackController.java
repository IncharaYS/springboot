package com.tcs.parcel_management.controller;

import com.tcs.parcel_management.dto.FeedbackRequestDTO;
import com.tcs.parcel_management.dto.FeedbackResponseDTO;
import com.tcs.parcel_management.dto.FeedbackViewDTO;
import com.tcs.parcel_management.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> submitFeedback(
            @Valid @RequestBody FeedbackRequestDTO request) {

        return ResponseEntity.ok(
                feedbackService.submitFeedback(request)
        );
    }


    @GetMapping
    public ResponseEntity<List<FeedbackViewDTO>> getAllFeedback() {

        return ResponseEntity.ok(
                feedbackService.getAllFeedback()
        );
    }
}