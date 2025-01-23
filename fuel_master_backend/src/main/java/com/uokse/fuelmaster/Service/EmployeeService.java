package com.uokse.fuelmaster.Service;

import com.uokse.fuelmaster.DTO.Request.EmployeeDTO;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.repository.EmployeeRepository;

public class EmployeeService {

    private EmployeeRepository employeeRepo;
    public String addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(
                employeeDTO.getId(),
                employeeDTO.getName(),
                employeeDTO.getPhone(),
                employeeDTO.getNic(),
                employeeDTO.getPassword(),
                employeeDTO.getFuelStation());

        employeeRepo.save(employee);

        return employee.getName();
    }
}
