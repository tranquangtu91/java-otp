package com.example.otp.controller;
import com.example.otp.dto.OtpRequestDto;
import com.example.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class OtpController {
    private final OtpService otpService;
    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }
    // API để đăng ký user và lấy QR Code
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody OtpRequestDto request) {
        try {
            String base64Image = otpService.registerUser(request.getUserName());
            return ResponseEntity.ok("data:image/png;base64," + base64Image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating QR Code");
        }
    }
    @GetMapping("/qrcode/{userName}")
    public ResponseEntity<String> getQRCode(@PathVariable String userName) {
        try {
            String base64Image = otpService.registerUser(userName);
            return ResponseEntity.ok("data:image/png;base64," + base64Image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating QR Code");
        }
    }
    // API để xác thực OTP
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyOtp(@RequestBody OtpRequestDto request) {
        boolean isValid = otpService.verifyOtp(request.getUserName(), request.getOtp());
        return ResponseEntity.ok(isValid);
    }
}
