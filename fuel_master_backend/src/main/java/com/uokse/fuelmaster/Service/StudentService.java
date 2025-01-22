package com.uokse.fuelmaster.service;

import java.util.List;

import com.uokse.fuelmaster.model.Student;

public interface StudentService {
    public Student saveStudent(Student student);
    public List<Student> getAllStudents();
}
