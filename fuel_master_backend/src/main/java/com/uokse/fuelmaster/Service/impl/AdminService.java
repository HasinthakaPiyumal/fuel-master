package com.uokse.fuelmaster.Service.impl;

import com.uokse.fuelmaster.DTO.AdminDTO;
import com.uokse.fuelmaster.Entity.Admin;
import com.uokse.fuelmaster.Repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Method to add an admin
    public String addAdmin(AdminDTO adminDTO) {

        // Create an admin object from the DTO
        Admin admin = new Admin(
                adminDTO.getId(),
                adminDTO.getName(),
                adminDTO.getPhone(),
                adminDTO.getNic(),
                adminDTO.getPassword(),
                adminDTO.getCreatedAt(),
                adminDTO.getUpdatedAt()
        );

        // Save the admin object to the database
        adminRepository.save(admin);


        return admin.getName();
    }

    // Method to check if the admin ID is valid
    public boolean isAdmin(Long adminId) {
        // This is just a placeholder. Replace with actual admin validation logic.
        return adminId != null && adminId > 0;
    }
}
