package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.MobileVerificationDTO;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.VerificationCodeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/verification")
@PreAuthorize("hasRole('USER')")
@Tag(name = "Verification", description = "Verification API")
@SecurityRequirement(name = "bearerAuth")
public class VerificationController {

    final VerificationCodeService verificationCodeService;

    public VerificationController(VerificationCodeService verificationCodeService) {
        this.verificationCodeService = verificationCodeService;
    }

    @GetMapping("/resend")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity sendVerificationCode() {
        Boolean status;
        try {
            status = verificationCodeService.sendVerificationCode();
            SuccessResponse response = new SuccessResponse(status ? "Verification code sent successfully" : "Failed to send verification code", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error sending verification code: " + e.getMessage());
            ErrorResponse response = new ErrorResponse(400, "Error sending verification code");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }
    @PostMapping("/verify")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity verifyPhoneNumber(@RequestBody @Valid MobileVerificationDTO mobileVerificationDTO) {
        Boolean status;
        try {
            status = verificationCodeService.verifyCode(mobileVerificationDTO.getCode());
            SuccessResponse response = new SuccessResponse("Successfully verified your phone number", true, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            ErrorResponse response = new ErrorResponse(400, e.getMessage());
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }
}