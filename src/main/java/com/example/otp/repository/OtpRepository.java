package com.example.otp.repository;

import com.example.otp.entity.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpRequest, Long> {
    Optional<OtpRequest> findByUsername(String username);
}
