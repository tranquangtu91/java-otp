package com.example.otp.service;

import com.example.otp.dto.OtpResponseDto;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface OtpService {
//    public String registerUser(String userName) throws IOException, WriterException;
    public OtpResponseDto registerUser(String userName) throws IOException, WriterException;
    public boolean verifyOtp(String secretKey, int otp);
}
