package com.company.coursemanagement.controller;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public EnrollmentDTO enroll(@RequestParam Long studentId, @RequestParam Long courseId) {
        return enrollmentService.enrollStudent(studentId, courseId);
    }

    @GetMapping
    public List<EnrollmentDTO> findAll() {
        return enrollmentService.findAll();
    }

    @GetMapping("/{id}")
    public EnrollmentDTO findById(@PathVariable Long id) {
        return enrollmentService.findById(id);
    }

    @PatchMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        enrollmentService.cancelEnrollment(id);
    }
}