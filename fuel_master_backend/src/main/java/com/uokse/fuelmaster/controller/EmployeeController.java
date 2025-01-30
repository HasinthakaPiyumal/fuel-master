package com.uokse.fuelmaster.controller;


import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.response.ErrorResponse;
import com.uokse.fuelmaster.response.SuccessResponse;
import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
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
@RequestMapping("/api/v1/employee")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping(path="/save")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Extract validation error messages
            HashMap<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }
        try {
            String name = employeeService.addEmployee(employeeDTO);
            HashMap<String, Object> data = new HashMap<>();
            data.put("employeeName", name);

            SuccessResponse successResponse = new SuccessResponse(
                    "User saved successfully",
                    true,
                    data
            );
            return ResponseEntity.ok(successResponse);

        } catch (IllegalArgumentException e) {  // Catch the specific exception
            ErrorResponse errorResponse = new ErrorResponse(404, e.getMessage());
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees(){
        List<EmployeeViewDetailsDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No employees found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    @GetMapping("/{phone}")
    public ResponseEntity<?> getEmployeeByPhone(@PathVariable String phone){
        EmployeeViewDetailsDTO employee = employeeService.getEmployeeByPhone(phone);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(404, "No Employee Found");
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

}
