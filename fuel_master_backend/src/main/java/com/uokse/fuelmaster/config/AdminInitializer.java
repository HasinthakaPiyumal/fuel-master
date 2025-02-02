package com.uokse.fuelmaster.config;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.model.AdminType;
import com.uokse.fuelmaster.repository.AdminRepository;
import com.uokse.fuelmaster.service.AdminService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminInitializer {
@Bean
ApplicationRunner initAdmin(AdminRepository adminRepository, AdminService adminService) {
    return args -> {
        if (adminRepository.findAll().isEmpty()) {
            AdminDTO admin = new AdminDTO();
            admin.setName("John Doe");
            admin.setEmail("admin@fuelmaster.com");
            admin.setRole(AdminType.SUPER_ADMIN);
            admin.setPassword("admin");
            admin.setNic("123456789V");
            adminService.addAdmin(admin);
            System.out.println("✅ Default ADMIN user created successfully.");
        }
    };
}}