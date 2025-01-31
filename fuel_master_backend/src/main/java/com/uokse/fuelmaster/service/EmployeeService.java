package com.uokse.fuelmaster.service;

import com.uokse.fuelmaster.dto.EmployeeViewDetailsDTO;
import com.uokse.fuelmaster.dto.LoginDTO;
import com.uokse.fuelmaster.dto.Request.EmployeeDTO;
import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.model.User;
import com.uokse.fuelmaster.repository.EmployeeRepository;
import com.uokse.fuelmaster.repository.FuelStationRepo;
import com.uokse.fuelmaster.util.PasswordUtil;

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
        // Check if NIC already exists
        Optional<Employee> existingEmployee = employeeRepo.findByNic(employeeDTO.getNic());
        if (existingEmployee.isPresent()) {
            throw new IllegalArgumentException("NIC already registered: " + employeeDTO.getNic());
        }
            // Check if NIC already exists
            Optional<Employee> existingEmployeeByNIC = employeeRepo.findByNic(employeeDTO.getNic());
            Optional<Employee> existingEmployeeByPhone = employeeRepo.findByPhone(employeeDTO.getPhone());
            if (existingEmployeeByNIC.isPresent()) {
                throw new IllegalArgumentException("NIC already registered: " + employeeDTO.getNic());
            }
            if (existingEmployeeByPhone.isPresent()) {
                throw new IllegalArgumentException("Phone number already registered: " + employeeDTO.getPhone());
            }

        // Check if Fuel Station exists
        FuelStation fuelStation = fuelStationRepo.findById(employeeDTO.getFuelStation())
                .orElseThrow(() -> new IllegalArgumentException("Fuel Station not found"));

        String hashedPassword = PasswordUtil.hashPassword(employeeDTO.getPassword());
        Employee employee = new Employee(
                null,
                employeeDTO.getName(),
                employeeDTO.getPhone(),
                employeeDTO.getNic(),
                hashedPassword,
                fuelStation,
                null, null);

        employeeRepo.save(employee);
        return employee.getName();

    }

    public List<EmployeeViewDetailsDTO> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream().map(employee -> new EmployeeViewDetailsDTO(
                employee.getName(),
                employee.getPhone(),
                employee.getNic())).toList();
    }

    public EmployeeViewDetailsDTO getEmployeeByPhone(String phone) {
        Optional<Employee> employeeOptional = employeeRepo.findByPhone(phone);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            return new EmployeeViewDetailsDTO(
                    employee.getName(),
                    employee.getPhone(),
                    employee.getNic());
        } else {
            return null;
        }
    }
    public Optional<Employee> getEmployee(String id) {
        Optional<Employee> employeeOptional = employeeRepo.findById(Long.parseLong(id));
        return employeeOptional;
    }

    public Optional<Employee> loginEmployee(LoginDTO loginDTO) {
        Optional<Employee> employee = employeeRepo.findByPhone(loginDTO.getPhone());
        if (employee.isPresent()) {
            String inputPassword = loginDTO.getPassword();
            String storedPassword = employee.get().getPassword();
            if (PasswordUtil.verifyPassword(inputPassword, storedPassword)) {
                return employee;
            }
        }
        return Optional.empty();
    }
}
