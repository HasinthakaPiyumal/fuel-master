package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.Response.AdminViewDTO;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.AdminService;
import com.uokse.fuelmaster.dto.AdminDTO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/admin/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path="/save")
    public ResponseEntity<?> saveAdmin(@Valid @RequestBody AdminDTO adminDTO, BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            // Extract validation error messages
            HashMap<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }
        try{
            Long id =adminService.addAdmin(adminDTO);
            HashMap<String, Object> data = new HashMap<>();
            data.put("UserId", id);
            SuccessResponse successResponse = new SuccessResponse(
                    "User saved successfully",
                    true,
                    data
            );
            return ResponseEntity.ok(successResponse);
        }catch (IllegalArgumentException e) {  // Catch the specific exception
            ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
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
