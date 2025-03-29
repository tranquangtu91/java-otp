package com.example.otp.service;

import com.example.otp.dto.OtpResponseDto;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface OtpService {
    public OtpResponseDto registry(String issuer) throws IOException, WriterException;
    public boolean verifyOtp(String secretKey, int otp);
}