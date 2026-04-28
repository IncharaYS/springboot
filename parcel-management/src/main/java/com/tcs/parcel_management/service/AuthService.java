package com.tcs.parcel_management.service;

import com.tcs.parcel_management.dto.AuthResponseDTO;
import com.tcs.parcel_management.dto.LoginRequestDTO;
import com.tcs.parcel_management.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}