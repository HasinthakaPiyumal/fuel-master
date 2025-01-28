package com.uokse.fuelmaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.uokse.fuelmaster.dto.Response.AdminViewDTO;
import com.uokse.fuelmaster.dto.UserDTO;
import com.uokse.fuelmaster.service.AdminService;
import com.uokse.fuelmaster.dto.AdminDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/admin/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path="/save")
    public String saveAdmin(@RequestBody AdminDTO adminDTO ){
        String id = adminService.addAdmin(adminDTO);
        return ("Admin saved with ID: " + id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAdmins() {
        List<AdminViewDTO> admins = adminService.getAllAdmins();
        if (!admins.isEmpty()) {
            return ResponseEntity.ok(admins);
        } else {
            return ResponseEntity.status(404).body("No admins found");
        }
    }

    @GetMapping("/{phone}")
    public ResponseEntity<?> getAdminByPhone(@RequestParam String phone) {
        List<AdminViewDTO> admins = adminService.getAdminByPhone(phone);
        if (!admins.isEmpty()) {
            return ResponseEntity.ok(admins);
        } else {
            return ResponseEntity.status(404).body("No admins found");
        }
    }




}
