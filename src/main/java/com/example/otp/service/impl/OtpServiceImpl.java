package com.example.otp.service.impl;

import com.example.otp.config.GoogleAuthConfig;
import com.example.otp.entity.OtpRequest;
import com.example.otp.repository.OtpRepository;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final GoogleAuthConfig googleAuthConfig;
    private final OtpRepository otpRepository;
//  test nhanh k can DB
    private final Map<String, String> fakeDb = new HashMap<>();

    private String generateQRCodeBase64(String otpAuthUrl) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthUrl, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    @Override
    public String registerUser(String userName) throws IOException, WriterException {
//        Optional<OtpRequest> existingUser = otpRepository.findByUsername(userName);
//        if (existingUser.isPresent()) {
//            return googleAuthConfig.generateQRCode(userName, existingUser.get().getSecretKey());
//        }
//        // Nếu không có, tạo secret key
          String secretKey = googleAuthConfig.generateSecretKey();
          if (secretKey == null) {
            throw new IllegalStateException("Secret Key is null.");
          }
//        OtpRequest user = new OtpRequest();
//        user.setUsername(userName);
//        user.setSecretKey(secretKey);
//        userRepository.save(user);
//
//        // Trả về QR Code URL
//        return googleAuthConfig.generateQRCode(userName, secretKey);
        fakeDb.putIfAbsent(userName, secretKey);
        String qrCodeUrl = googleAuthConfig.generateQRCode(userName, fakeDb.get(userName));
        return generateQRCodeBase64(qrCodeUrl);
    }

    @Override
    public boolean verifyOtp(String userName, int otp) {
//        // Lấy secretKey từ database
//        Optional<OtpRequest> user = otpRepository.findByUsername(userName);
//        if (user.isEmpty()) return false;
//
//        // Kiểm tra mã OTP
//        return googleAuthConfig.verifyOTP(user.get().getSecretKey(), otp);
        return googleAuthConfig.verifyOTP(fakeDb.get(userName), otp);
    }

}
