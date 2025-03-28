package com.example.otp.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface OtpService {
    public String registerUser(String userName) throws IOException, WriterException;
    public boolean verifyOtp(String userName, int otp);
//    public String generateQRCodeBase64(String otpAuthUrl) throws WriterException, IOException;
}
