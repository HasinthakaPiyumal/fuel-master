package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.repository.EmployeeRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private FuelStationRepo fuelStationRepo;

    public String addEmployee(EmployeeDTO employeeDTO) {

        Employee employee = null;
        try {
            FuelStation fuelStation = fuelStationRepo.findById(employeeDTO.getFuelStation()).orElseThrow();

            employee = new Employee(
                    null,
                    employeeDTO.getName(),
                    employeeDTO.getPhone(),
                    employeeDTO.getNic(),
                    employeeDTO.getPassword(),
                    fuelStation,
                    null,null
            );

            employeeRepo.save(employee);

            return employee.getName();
        } catch (Exception e) {
            System.out.println("Employee registration failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
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
