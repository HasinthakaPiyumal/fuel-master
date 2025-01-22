package com.uokse.fuelmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uokse.fuelmaster.model.Student;

@Repository
public interface  StudentRepository extends JpaRepository<Student, Integer> {

}
