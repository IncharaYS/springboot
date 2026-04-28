package com.tcs.parcel_management.service.impl;

import com.tcs.parcel_management.constants.Role;
import com.tcs.parcel_management.dto.AuthResponseDTO;
import com.tcs.parcel_management.dto.LoginRequestDTO;
import com.tcs.parcel_management.dto.RegisterRequestDTO;
import com.tcs.parcel_management.entity.UserEntity;
import com.tcs.parcel_management.exception.BusinessValidationException;
import com.tcs.parcel_management.exception.DuplicateResourceException;
import com.tcs.parcel_management.exception.InvalidCredentialsException;
import com.tcs.parcel_management.repository.UserRepository;
import com.tcs.parcel_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already registered");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessValidationException("Passwords do not match");
        }

        Integer maxCustomerId = userRepository.findMaxCustomerId();
        Integer nextCustomerId = (maxCustomerId == null) ? 1001 : maxCustomerId + 1;

        UserEntity user = new UserEntity();
        user.setCustomerId(nextCustomerId);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPassword(request.getPassword()); // Will encode later
        user.setRole(Role.CUSTOMER);
        user.setActive(true);

        userRepository.save(user);

        return new AuthResponseDTO(
                null,
                user.getRole().name(),
                "Registration successful"
        );
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (!user.getActive()) {
            throw new BusinessValidationException("Account is inactive");
        }

        return new AuthResponseDTO(
                null,
                user.getRole().name(),
                "Login successful"
        );
    }
}