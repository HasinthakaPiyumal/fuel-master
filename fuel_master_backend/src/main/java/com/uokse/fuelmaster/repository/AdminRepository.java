package com.uokse.fuelmaster.repository;

import com.uokse.fuelmaster.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uokse.fuelmaster.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    List<Admin> findByPhone(String phone);

}
