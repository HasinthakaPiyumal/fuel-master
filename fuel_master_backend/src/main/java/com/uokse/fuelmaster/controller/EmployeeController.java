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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/save")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            String id = employeeService.addEmployee(employeeDTO);
            HashMap<String, Object> data = new HashMap<>();
            data.put("employeeName", id);

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
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeViewDetailsDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No employees found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/{phone}")
    public ResponseEntity<?> getEmployeeByPhone(@PathVariable String phone) {
        EmployeeViewDetailsDTO employee = employeeService.getEmployeeByPhone(phone);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No Employee Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO) {
        Optional<Employee> employee = employeeService.loginEmployee(loginDTO);
        if (employee.isPresent()) {
            String token = jwtService.generateToken(employee.get());
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
