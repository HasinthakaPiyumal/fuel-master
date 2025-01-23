package com.uokse.fuelmaster.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthErrorResponse {
    private int status;
    private String error;
    private String message;
    private long timestamp;
} 