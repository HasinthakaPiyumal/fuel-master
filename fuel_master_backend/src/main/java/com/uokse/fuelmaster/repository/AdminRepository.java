package com.uokse.fuelmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uokse.fuelmaster.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
