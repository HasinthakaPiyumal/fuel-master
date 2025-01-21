package com.uokse.fuelmaster.Repo;

import com.uokse.fuelmaster.Entity.User;
import com.uokse.fuelmaster.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUser(User user);


   Optional<Vehicle> findByChassisNumber(String chassisNumber);


    Optional<Vehicle> findByVehicleRegistrationPart1AndVehicleRegistrationPart2(String part1, Long part2);
}
