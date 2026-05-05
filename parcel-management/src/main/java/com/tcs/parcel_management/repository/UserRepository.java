package com.tcs.parcel_management.repository;

import com.tcs.parcel_management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT MAX(u.customerId) FROM UserEntity u")
    Integer findMaxCustomerId();

    Optional<UserEntity> findByCustomerId(Integer customerId);
}