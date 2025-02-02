package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.dto.AdminLoginDTO;
import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.Response.AdminViewDTO;
import com.uokse.fuelmaster.model.Admin;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.repository.AdminRepository;

import com.uokse.fuelmaster.util.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.uokse.fuelmaster.util.PasswordUtil.hashPassword;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;


    // Method to add an admin
    @Transactional
    public String addAdmin(AdminDTO adminDTO) {

        if(adminRepository.findByNic(adminDTO.getNic()).isPresent()){
            throw new IllegalArgumentException("NIC already registered: " +adminDTO.getNic());
        }
        if (adminRepository.findByEmail(adminDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Phone number already registered: " + adminDTO.getEmail());
        }
        Admin admin = new Admin();
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setNic(adminDTO.getNic());
        admin.setRole(adminDTO.getRole());
        admin.setPassword(hashPassword(adminDTO.getPassword()));

        // Save the admin object to the database
        adminRepository.save(admin);
        return admin.getName();
    }


    public List<AdminViewDTO> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return admins.stream().map(admin -> new AdminViewDTO(
                admin.getName(),
                admin.getEmail(),
                admin.getNic()
        )).toList();
    }


    public Optional<Admin> loginAdmin(AdminLoginDTO loginDTO) {
        Optional<Admin> user = adminRepository.findByEmail(loginDTO.getEmail());
        if (user.isPresent()) {
            String inputPassword = loginDTO.getPassword();
            String storedPassword = user.get().getPassword();
            if (PasswordUtil.verifyPassword(inputPassword, storedPassword)) {
                return user;
            }

        }
        return Optional.empty();
    }

    public Optional<Admin> getAdmin(String id) {
        Optional<Admin> adminOptional = adminRepository.findById(Long.parseLong(id));
        return adminOptional;
    }
}
