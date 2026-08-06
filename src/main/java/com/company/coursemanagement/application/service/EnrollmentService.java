package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.domain.exception.BusinessException;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.exception.EnrollmentNotFoundException;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.model.EnrollmentStatus;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public EnrollmentDTO enrollStudent(Long studentId, Long courseId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        long activeEnrollments = enrollmentRepository.countByCourseIdAndStatus(courseId, EnrollmentStatus.ACTIVE);
        if (activeEnrollments >= course.getMaxCapacity()) {
            throw new BusinessException("El curso " + course.getName() + " ya alcanzó su capacidad máxima (" + course.getMaxCapacity() + ").");
        }

        Enrollment enrollment = new Enrollment(null, studentId, courseId, LocalDate.now(), EnrollmentStatus.ACTIVE);
        Enrollment saved = enrollmentRepository.save(enrollment);
        return toDTO(saved);
    }

    public EnrollmentDTO findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        return toDTO(enrollment);
    }

    public List<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public void cancelEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);
    }

    private EnrollmentDTO toDTO(Enrollment enrollment) {
        return new EnrollmentDTO(enrollment.getId(), enrollment.getStudentId(), enrollment.getCourseId(), enrollment.getEnrollmentDate(), enrollment.getStatus());
    }
}