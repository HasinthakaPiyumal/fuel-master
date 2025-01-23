package com.uokse.fuelmaster.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.uokse.fuelmaster.service.AdminService;
import com.uokse.fuelmaster.dto.AdminDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path="/save")
    public String saveAdmin(@RequestBody AdminDTO adminDTO ){
        String id = adminService.addAdmin(adminDTO);
        return ("Admin saved with ID: " + id);
    }


}
