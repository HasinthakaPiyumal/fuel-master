package com.uokse.fuelmaster.Controller;


import com.uokse.fuelmaster.DTO.Request.EmployeeDTO;
import com.uokse.fuelmaster.Service.EmployeeService;
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
