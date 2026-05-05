package com.tcs.parcel_management.repository;

import com.tcs.parcel_management.entity.CardEntity;
import com.tcs.parcel_management.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    Optional<CardEntity> findByCardNumberAndCvvAndExpiryMonthAndExpiryYearAndActiveTrue(
            String cardNumber,
            String cvv,
            Integer expiryMonth,
            Integer expiryYear
    );
}