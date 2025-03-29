package com.example.otp.dto;

import lombok.Data;

@Data
public class OtpRequestDto {
    private String userName;
    private String secretKey;
    private int otp;
}
