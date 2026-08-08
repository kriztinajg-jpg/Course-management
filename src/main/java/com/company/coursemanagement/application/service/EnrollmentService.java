package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO enrollStudent(Long studentId, Long courseId);
    EnrollmentDTO findById(Long id);
    List<EnrollmentDTO> findAll();
    void cancelEnrollment(Long id);
}