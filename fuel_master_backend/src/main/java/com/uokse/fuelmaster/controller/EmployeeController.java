package com.uokse.fuelmaster.controller;


import com.uokse.fuelmaster.service.EmployeeService;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

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

}
