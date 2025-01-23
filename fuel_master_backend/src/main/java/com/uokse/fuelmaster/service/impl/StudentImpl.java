package com.uokse.fuelmaster.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uokse.fuelmaster.model.Student;
import com.uokse.fuelmaster.repository.StudentRepository;
import com.uokse.fuelmaster.service.StudentService;

@Service
public class StudentImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    
    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
