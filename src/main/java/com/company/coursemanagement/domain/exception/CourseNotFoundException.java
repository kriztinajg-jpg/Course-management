package com.company.coursemanagement.domain.exception;

public class CourseNotFoundException extends BusinessException {
    public CourseNotFoundException(Long id) {
        super("Course not found: " + id);
    }
}