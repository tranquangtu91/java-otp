package com.example.otp.service.impl;

import com.example.otp.config.GoogleAuthConfig;
import com.example.otp.dto.OtpResponseDto;
import com.example.otp.service.OtpService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final GoogleAuthConfig googleAuthConfig;

    private String generateQRCodeBase64(String otpAuthUrl) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public OtpResponseDto registry(String issuer) throws IOException, WriterException {
        String secretKey = googleAuthConfig.generateSecretKey();
        String qrCodeUrl = googleAuthConfig.generateQRCode(issuer, secretKey);
        log.info("Generated OTP for user: {}", issuer);
        return new OtpResponseDto(issuer, secretKey, "data:image/png;base64," + generateQRCodeBase64(qrCodeUrl));
    }

    @Override
    public boolean verifyOtp(String secretKey , int otp) {
        return googleAuthConfig.verifyOTP(secretKey, otp);
    }

}
