package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}