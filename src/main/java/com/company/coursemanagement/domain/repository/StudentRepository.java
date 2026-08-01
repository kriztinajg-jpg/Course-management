package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.domain.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(Long id);
    List<Student> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}