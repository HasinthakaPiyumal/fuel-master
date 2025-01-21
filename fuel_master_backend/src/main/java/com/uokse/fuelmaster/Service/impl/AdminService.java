package com.uokse.fuelmaster.Service.impl;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    // Method to check if the admin ID is valid
    public boolean isAdmin(Long adminId) {
        // This is just a placeholder. Replace with actual admin validation logic.
        return adminId != null && adminId > 0;
    }
}
