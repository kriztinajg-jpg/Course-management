package com.company.coursemanagement;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.presentation.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ApiGestionDeCursosApplication implements CommandLineRunner {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public ApiGestionDeCursosApplication(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiGestionDeCursosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Solo carga estudiantes de prueba si la base está vacía (para no duplicar en cada arranque)
        if (studentService.findAll().isEmpty()) {
            studentService.create(new StudentDTO(null, "Juan", "Perez", "juan@test.com", LocalDate.of(2000, 1, 15)));
            studentService.create(new StudentDTO(null, "Maria", "Gomez", "maria@test.com", LocalDate.of(2002, 5, 20)));
        }

        ConsoleMenu consoleMenu = new ConsoleMenu(studentService, courseService, enrollmentService);
        consoleMenu.start();
    }
}