package com.uokse.fuelmaster.controller;

import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import com.uokse.fuelmaster.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final JwtService jwtService;

    public EmployeeController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(path="/save")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Define the expected field order
            List<String> fieldOrder = Arrays.asList("name", "phone", "nic", "password", "fuelStation");

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
            String name = employeeService.addEmployee(employeeDTO);
            HashMap<String, Object> data = new HashMap<>();
            data.put("employeeName", name);

            SuccessResponse successResponse = new SuccessResponse(
                    "User saved successfully",
                    true,
                    data);
            return ResponseEntity.ok(successResponse);

        } catch (IllegalArgumentException e) { // Catch the specific exception
            ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage());
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeViewDetailsDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("allEmployees", employees);

            SuccessResponse successResponse = new SuccessResponse(
                    "Employees retrieved successfully",
                    true,
                    data
            );

            return ResponseEntity.ok(successResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No employees found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/{phone}")
    @PreAuthorize("hasAnyRole('STATION_MANAGER','SUPER_ADMIN')")
    public ResponseEntity<?> getEmployeeByPhone(@PathVariable String phone) {
        EmployeeViewDetailsDTO employee = employeeService.getEmployeeByPhone(phone);
        if (employee != null) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", employee);
            SuccessResponse successResponse = new SuccessResponse(
                    "Employee retrieved successfully",
                    true,
                    data
            );

            return ResponseEntity.ok(successResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No Employee Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO) {
        Optional<Employee> employee = employeeService.loginEmployee(loginDTO);
        if (employee.isPresent()) {
            String token = jwtService.generateToken(employee.get(),"EMPLOYEE");
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", employee.get().getCommonData());
            data.put("token", token);
            SuccessResponse response = new SuccessResponse("Successfully logged in.", true, data);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(401, "Invalid Credentials"));
        }
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> getEmployeeByToken(@RequestHeader("Authorization") String bearerToken) {
        final String jwt = bearerToken.substring(7);
        String phone = "";
        try {
            phone = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON).body(new ErrorResponse(401, "Invalid Token"));
        }
        Optional<Employee> employee = employeeService.getEmployee(phone);
        if (employee.isPresent()) {
            String token = jwtService.generateToken(employee.get());
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", employee.get().getCommonData());
            data.put("token", token);
            SuccessResponse response = new SuccessResponse("Employee found", true, data);
            return ResponseEntity.ok(response);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No Employee Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

}
