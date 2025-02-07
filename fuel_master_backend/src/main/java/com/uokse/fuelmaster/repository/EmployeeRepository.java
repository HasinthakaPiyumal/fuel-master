package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByPhone(String phone);

    Optional<Employee> findByNic(String nic);

    Optional<Employee> findOneByPhoneAndPassword(String phone, String password);

    void deleteByFuelStationId(Long id);
}
