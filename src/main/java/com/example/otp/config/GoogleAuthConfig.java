package com.example.otp.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuthConfig {
    private final GoogleAuthenticator gAuth;

    public GoogleAuthConfig() {
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder()
                .setTimeStepSizeInMillis(30000) // Mỗi OTP có hiệu lực trong 30 giây
                .setWindowSize(1) // Chỉ chấp nhận OTP của thời điểm hiện tại
                .build();
        this.gAuth = new GoogleAuthenticator(config);
    }
    /**
     * Tạo một secret key mới cho user
     */
    public String generateSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        if (key == null || key.getKey() == null) {
            throw new IllegalStateException("Failed to generate a valid secret key.");
        }
        return key.getKey();
    }

    /**
     * Tạo URL QRCode để quét vào Google Authenticator
     */
    public String generateQRCode(String issuer, String secretKey) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("Secret key cannot be null or empty.");
        }
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s&algorithm=SHA1&digits=6&period=30",
                "TPbank Authenticator",
                issuer,
                secretKey,
                "TPbank Authenticator");
    }


    /**
     * Xác thực mã OTP nhập vào từ Google Authenticator
     */
    public boolean verifyOTP(String secretKey, int otp) {
        return gAuth.authorize(secretKey, otp);
    }
}
