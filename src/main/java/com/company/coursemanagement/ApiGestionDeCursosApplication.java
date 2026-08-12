package com.company.coursemanagement;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.application.service.impl.CourseServiceImpl;
import com.company.coursemanagement.application.service.impl.EnrollmentServiceImpl;
import com.company.coursemanagement.application.service.impl.StudentServiceImpl;
import com.company.coursemanagement.domain.repository.CourseRepository;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;
import com.company.coursemanagement.domain.repository.StudentRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryCourseRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryEnrollmentRepository;
import com.company.coursemanagement.infrastructure.persistence.InMemoryStudentRepository;
import com.company.coursemanagement.presentation.ConsoleMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ApiGestionDeCursosApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // 1. Inicia el framework Spring Boot
        SpringApplication.run(ApiGestionDeCursosApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        StudentRepository studentRepository = new InMemoryStudentRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();
        EnrollmentRepository enrollmentRepository = new InMemoryEnrollmentRepository();


        StudentService studentService = new StudentServiceImpl(studentRepository);
        CourseService courseService = new CourseServiceImpl(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentServiceImpl(enrollmentRepository, studentRepository, courseRepository);

        // 4. (Opcional) Cargar estudiantes iniciales para probar tus validaciones de inmediato
        studentService.create(new StudentDTO(null, "Juan", "Perez", "juan@test.com", LocalDate.of(2000, 1, 15)));
        studentService.create(new StudentDTO(null, "Maria", "Gomez", "maria@test.com", LocalDate.of(2002, 5, 20)));

        // 5. Instanciar e Iniciar el Menú de Consola
        ConsoleMenu consoleMenu = new ConsoleMenu(studentService, courseService, enrollmentService);
        consoleMenu.start();
    }
}