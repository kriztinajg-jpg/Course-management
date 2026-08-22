package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}