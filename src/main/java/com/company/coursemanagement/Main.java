package com.company.coursemanagement;

import com.company.coursemanagement.application.service.*;
import com.company.coursemanagement.application.service.impl.CourseServiceImpl;
import com.company.coursemanagement.application.service.impl.EnrollmentServiceImpl;
import com.company.coursemanagement.application.service.impl.StudentServiceImpl;
import com.company.coursemanagement.domain.repository.*;
import com.company.coursemanagement.infrastructure.persistence.*;
import com.company.coursemanagement.presentation.ConsoleMenu;

public class Main {
    public static void main(String[] args) {

        StudentRepository studentRepository = new InMemoryStudentRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepository = new InMemoryEnrollmentRepository();


        StudentService studentService = new StudentServiceImpl(studentRepository);
        CourseService courseService = new CourseServiceImpl(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentServiceImpl(enrollmentRepository, studentRepository, courseRepository);

        // 3. Pasar los servicios a la vista
        ConsoleMenu consoleMenu = new ConsoleMenu(studentService, courseService, enrollmentService);

        // 4. Iniciar menú
        consoleMenu.start();
    }
}