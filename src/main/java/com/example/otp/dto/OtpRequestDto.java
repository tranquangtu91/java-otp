package com.example.otp.dto;

import lombok.Data;

@Data
public class OtpRequestDto {
    private String issuer;
    private String secretKey;
    private int otp;
}
