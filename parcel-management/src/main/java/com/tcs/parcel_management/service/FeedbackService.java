package com.tcs.parcel_management.service;

import com.tcs.parcel_management.dto.FeedbackRequestDTO;
import com.tcs.parcel_management.dto.FeedbackResponseDTO;
import com.tcs.parcel_management.dto.FeedbackViewDTO;

import java.util.List;

public interface FeedbackService {

    FeedbackResponseDTO submitFeedback(FeedbackRequestDTO request);

    List<FeedbackViewDTO> getAllFeedback();
}