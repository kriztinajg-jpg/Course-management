package com.company.coursemanagement;

import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryCourseRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryEnrollmentRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryStudentRepository;
import com.company.coursemanagement.presentation.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        StudentRepository studentRepository = new InMemoryStudentRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepository = new InMemoryEnrollmentRepository();

        StudentService studentService = new StudentService(studentRepository);
        CourseService courseService = new CourseService(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepository, studentRepository, courseRepository);

        ConsoleMenu consoleMenu = new ConsoleMenu(studentService, courseService, enrollmentService);
        consoleMenu.start();
    }
}