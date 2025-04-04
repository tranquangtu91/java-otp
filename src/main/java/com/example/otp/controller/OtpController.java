package com.example.otp.controller;
import com.example.otp.dto.OtpRequestDto;
import com.example.otp.dto.OtpResponseDto;
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
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody OtpRequestDto request) {
//        try {
//            String base64Image = otpService.registerUser(request.getUserName());
//            return ResponseEntity.ok("data:image/png;base64," + base64Image);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating QR Code");
//        }
//    }
    // API để đăng ký user và lấy QR code + secretKey
    @PostMapping("/register")
    public ResponseEntity<OtpResponseDto> registerUser(@RequestBody OtpRequestDto request) {
        try {
            OtpResponseDto otpResponseDto  = otpService.registry(request.getIssuer());
            return ResponseEntity.ok(otpResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OtpResponseDto());
        }
    }
    @GetMapping("/register/{userName}")
    public ResponseEntity<OtpResponseDto> getQRCode(@PathVariable String userName) {
        try {
            OtpResponseDto otpResponseDto  = otpService.registry(userName);
            return ResponseEntity.ok(otpResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OtpResponseDto());
        }
    }
    // API để xác thực OTP
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyOtp(@RequestBody OtpRequestDto request) {
        boolean isValid = otpService.verifyOtp(request.getSecretKey(), request.getOtp());
        return ResponseEntity.ok(isValid);
    }
}
