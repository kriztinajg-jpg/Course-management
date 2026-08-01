package com.company.coursemanagement.domain.exception;

public class StudentNotFoundException extends BusinessException {
    public StudentNotFoundException(Long id) {
        super("Student not found: " + id);
    }
}