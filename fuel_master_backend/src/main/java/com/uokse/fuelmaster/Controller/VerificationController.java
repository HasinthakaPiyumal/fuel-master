package com.uokse.fuelmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uokse.fuelmaster.dto.MobileVerificationDTO;
import com.uokse.fuelmaster.response.VerificationResponse;
import com.uokse.fuelmaster.service.TwilioService;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    private TwilioService twilioService;

    @PostMapping("/send")
    public ResponseEntity<VerificationResponse> sendVerificationCode(
            @RequestBody MobileVerificationDTO request) {
        String status = twilioService.sendSmsNotification(request.getPhoneNumber(), "Your verification code is: ");
        return ResponseEntity.ok(new VerificationResponse(
                status,
                "Verification code sent successfully"));
    }
}