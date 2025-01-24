package com.uokse.fuelmaster.controller;


import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping(path="/save")
    public String saveEmployee(@RequestBody EmployeeDTO employeeDTO ){
        String id = employeeService.addEmployee(employeeDTO);
        return ("User saved with ID : " + id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees(){
        List<EmployeeViewDetailsDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.status(404).body("No users found");
        }
    }

    @GetMapping("/{phone}")
    public ResponseEntity<?> getEmployeeByPhone(@PathVariable String phone){
        EmployeeViewDetailsDTO employee = employeeService.getEmployeeByPhone(phone);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(404).body("No employee found");
        }
    }

}
