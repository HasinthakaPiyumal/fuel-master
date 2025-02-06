package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.AdminDTO;
import com.uokse.fuelmaster.dto.AdminLoginDTO;
import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import com.uokse.fuelmaster.dto.Response.AdminViewDTO;
import com.uokse.fuelmaster.model.Admin;
import com.uokse.fuelmaster.model.AdminType;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.AdminService;
import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.service.JwtService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin", description = "Admin API")
public class AdminController {

    private AdminService adminService;
    private final JwtService jwtService;

    public AdminController(AdminService adminService, JwtService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    @PostMapping(path="/save")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> saveAdmin(@Valid @RequestBody AdminDTO adminDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Define the expected field order
            List<String> fieldOrder = Arrays.asList("name", "email", "nic", "password", "role");

            // Get the first occurring field error based on the expected order
            for (String field : fieldOrder) {
                Optional<String> errorMessage = bindingResult.getFieldErrors().stream()
                        .filter(error -> error.getField().equals(field))
                        .map(FieldError::getDefaultMessage)
                        .findFirst();

                if (errorMessage.isPresent()) {
                    ErrorResponse errorResponse = new ErrorResponse(400, errorMessage.get());
                    return ResponseEntity.badRequest().body(errorResponse);
                }
            }
        }
        try {
            String name = adminService.addAdmin(adminDTO);
            HashMap<String, Object> data = new HashMap<>();
            data.put("name", name);

            SuccessResponse successResponse = new SuccessResponse(
                    "Admin saved successfully",
                    true,
                    data);
            return ResponseEntity.ok(successResponse);

        } catch (IllegalArgumentException e) { // Catch the specific exception
            ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage());
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAllAdmins() {
        List<AdminViewDTO> admins = adminService.getAllAdmins();
        if (!admins.isEmpty()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("allAdmins", admins);

            SuccessResponse successResponse = new SuccessResponse(
                    "Admins retrieved successfully",
                    true,
                    data
            );

            return ResponseEntity.ok(successResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No admin found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody AdminLoginDTO loginDTO,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Define the expected field order
            List<String> fieldOrder = Arrays.asList("name", "email", "nic", "password", "role");

            // Get the first occurring field error based on the expected order
            for (String field : fieldOrder) {
                Optional<String> errorMessage = bindingResult.getFieldErrors().stream()
                        .filter(error -> error.getField().equals(field))
                        .map(FieldError::getDefaultMessage)
                        .findFirst();

                if (errorMessage.isPresent()) {
                    ErrorResponse errorResponse = new ErrorResponse(400, errorMessage.get());
                    return ResponseEntity.badRequest().body(errorResponse);
                }
            }
        }
        Optional<Admin> admin = adminService.loginAdmin(loginDTO);
        if (admin.isPresent()) {
            String role = admin.get().getRole()== AdminType.SUPER_ADMIN?"SUPER_ADMIN":"STATION_MANAGER";
            String token = jwtService.generateToken(admin.get(), role);
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", admin.get());
            data.put("token", token);
            SuccessResponse response = new SuccessResponse("Successfully logged in.", true, data);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(401, "Invalid Credentials"));
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> getAdminByToken(@RequestHeader("Authorization") String bearerToken) {
        final String jwt = bearerToken.substring(7);
        String id = "";
        try {
            id = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(401, "Invalid Token"));
        }
        Optional<Admin> admin = adminService.getAdmin(id);
        if (admin.isPresent()) {
            String token = jwtService.generateToken(admin.get());
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", admin.get());
            data.put("token", token);
            SuccessResponse response = new SuccessResponse("Admin found", true, data);
            return ResponseEntity.ok(response);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No Admin Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

}
