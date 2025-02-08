package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.Employee;
import com.uokse.fuelmaster.model.FuelStation;
import com.uokse.fuelmaster.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByPhone(String phone);

    Optional<Employee> findByNic(String nic);

    List<Employee> findByFuelStation(FuelStation id);

    Optional<Employee> findOneByPhoneAndPassword(String phone, String password);

    void deleteByFuelStationId(Long id);

    List<Employee> findAllByFuelStation( FuelStation fuelStation);
}
