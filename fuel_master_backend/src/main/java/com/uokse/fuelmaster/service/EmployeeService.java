package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

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

    public List<EmployeeViewDetailsDTO> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().map(employee -> new EmployeeViewDetailsDTO(
                employee.getName(),
                employee.getPhone(),
                employee.getNic()
        )).toList();
    }

    public EmployeeViewDetailsDTO getEmployeeByPhone(String phone) {
        Optional<Employee> employeeOptional = employeeRepo.findByPhone(phone);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return new EmployeeViewDetailsDTO(
                    employee.getName(),
                    employee.getPhone(),
                    employee.getNic()
            );
        } else {
            return null;
        }
    }
}
