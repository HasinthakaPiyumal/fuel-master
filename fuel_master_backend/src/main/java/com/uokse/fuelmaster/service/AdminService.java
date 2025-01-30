package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.dto.Response.AdminViewDTO;
import com.uokse.fuelmaster.model.Admin;
import com.uokse.fuelmaster.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;


    // Method to add an admin
    public Long addAdmin(AdminDTO adminDTO) {
        if(adminRepository.findByNic(adminDTO.getNic()).isPresent()){
            throw new IllegalArgumentException("NIC already registered"+adminDTO.getNic());
        }

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
        return admin.getId();
    }


    public List<AdminViewDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(admin -> new AdminViewDTO(
                admin.getName(),
                admin.getPhone(),
                admin.getNic()
        )).toList();
    }

    public List<AdminViewDTO> getAdminByPhone(String phone) {
        List<Admin> admins = adminRepository.findByPhone(phone);
        return admins.stream().map(admin -> new AdminViewDTO(
                admin.getName(),
                admin.getPhone(),
                admin.getNic()
        )).toList();
    }
}
