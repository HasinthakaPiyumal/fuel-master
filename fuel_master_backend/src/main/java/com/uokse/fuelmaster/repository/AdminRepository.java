package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uokse.fuelmaster.model.Admin;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByNic(String nic);

    @Query("SELECT a FROM Admin a WHERE a.role = 'STATION_MANAGER' " +
            "AND a.id NOT IN (SELECT f.ownerId FROM FuelStation f)")
    List<Admin> findUnassignedStationManagers();
}
