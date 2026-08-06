package com.company.coursemanagement.presentation;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.dto.EnrollmentDTO;
import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.application.service.EnrollmentService;
import com.company.coursemanagement.application.service.StudentService;

import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleMenu {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final Scanner scanner;

    public ConsoleMenu(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int option;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Students");
            System.out.println("2. Courses");
            System.out.println("3. Enrollments");
            System.out.println("0. Exit");
            System.out.print("Seleccione una opción: ");

            option = readInt();

            switch (option) {
                case 1 -> studentMenu();
                case 2 -> courseMenu();
                case 3 -> enrollmentMenu();
                case 0 -> System.out.println("\n¡Hasta pronto!");
                default -> System.out.println("Opción no válida.");
            }
        } while (option != 0);
    }

    private void studentMenu() {
        int option;
        do {
            System.out.println("\n--- MENÚ STUDENTS ---");
            System.out.println("1. Create");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");
            System.out.print("Opción: ");

            option = readInt();

            try {
                switch (option) {
                    case 1 -> createStudent();
                    case 2 -> findStudentById();
                    case 3 -> listAllStudents();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 0 -> System.out.println("Regresando...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void createStudent() {
        System.out.print("Nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Fecha Nacimiento (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        StudentDTO dto = new StudentDTO(null, firstName, lastName, email, birthDate);
        StudentDTO created = studentService.create(dto);
        System.out.println("✅ Estudiante creado. ID: " + created.getId());
    }

    private void findStudentById() {
        System.out.print("ID del estudiante: ");
        Long id = (long) readInt();
        StudentDTO student = studentService.findById(id);
        System.out.println("Encontrado: " + student.getFirstName() + " " + student.getLastName());
    }

    private void listAllStudents() {
        var students = studentService.findAll();
        if (students.isEmpty()) {
            System.out.println("No hay estudiantes.");
            return;
        }
        students.forEach(s -> System.out.println("[" + s.getId() + "] " + s.getFirstName() + " " + s.getLastName()));
    }

    private void updateStudent() {
        System.out.print("ID del estudiante a actualizar: ");
        Long id = (long) readInt();
        System.out.print("Nuevo Nombre: ");
        String firstName = scanner.nextLine();
        System.out.print("Nuevo Apellido: ");
        String lastName = scanner.nextLine();
        System.out.print("Nuevo Email: ");
        String email = scanner.nextLine();
        System.out.print("Nueva Fecha Nacimiento (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());

        StudentDTO dto = new StudentDTO(id, firstName, lastName, email, birthDate);
        studentService.update(id, dto);
        System.out.println("✅ Estudiante actualizado.");
    }

    private void deleteStudent() {
        System.out.print("ID a eliminar: ");
        Long id = (long) readInt();
        studentService.delete(id);
        System.out.println("✅ Estudiante eliminado.");
    }

    private void courseMenu() {
        int option;
        do {
            System.out.println("\n--- MENÚ COURSES ---");
            System.out.println("1. Create");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Update");
            System.out.println("5. Delete");
            System.out.println("0. Back");
            System.out.print("Opción: ");

            option = readInt();

            try {
                switch (option) {
                    case 1 -> createCourse();
                    case 2 -> findCourseById();
                    case 3 -> listAllCourses();
                    case 4 -> updateCourse();
                    case 5 -> deleteCourse();
                    case 0 -> System.out.println("Regresando...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void createCourse() {
        System.out.print("Código: ");
        String code = scanner.nextLine();
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Descripción: ");
        String description = scanner.nextLine();
        System.out.print("Capacidad Máxima: ");
        Integer maxCapacity = readInt();

        CourseDTO dto = new CourseDTO(null, code, name, description, maxCapacity);
        CourseDTO created = courseService.create(dto);
        System.out.println("✅ Curso creado. ID: " + created.getId());
    }

    private void findCourseById() {
        System.out.print("ID del curso: ");
        Long id = (long) readInt();
        CourseDTO course = courseService.findById(id);
        System.out.println("Encontrado: " + course.getName());
    }

    private void listAllCourses() {
        var courses = courseService.findAll();
        if (courses.isEmpty()) {
            System.out.println("No hay cursos.");
            return;
        }
        courses.forEach(c -> System.out.println("[" + c.getId() + "] " + c.getName()));
    }

    private void updateCourse() {
        System.out.print("ID del curso a actualizar: ");
        Long id = (long) readInt();
        System.out.print("Nuevo Código: ");
        String code = scanner.nextLine();
        System.out.print("Nuevo Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Nueva Descripción: ");
        String description = scanner.nextLine();
        System.out.print("Nueva Capacidad: ");
        Integer maxCapacity = readInt();

        CourseDTO dto = new CourseDTO(id, code, name, description, maxCapacity);
        courseService.update(id, dto);
        System.out.println("✅ Curso actualizado.");
    }

    private void deleteCourse() {
        System.out.print("ID a eliminar: ");
        Long id = (long) readInt();
        courseService.delete(id);
        System.out.println("✅ Curso eliminado.");
    }

    private void enrollmentMenu() {
        int option;
        do {
            System.out.println("\n--- MENÚ ENROLLMENTS ---");
            System.out.println("1. Create Enrollment");
            System.out.println("2. Find By Id");
            System.out.println("3. List All");
            System.out.println("4. Cancel Enrollment");
            System.out.println("0. Back");
            System.out.print("Opción: ");

            option = readInt();

            try {
                switch (option) {
                    case 1 -> createEnrollment();
                    case 2 -> findEnrollmentById();
                    case 3 -> listAllEnrollments();
                    case 4 -> cancelEnrollment();
                    case 0 -> System.out.println("Regresando...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void createEnrollment() {
        System.out.print("ID Estudiante: ");
        Long studentId = (long) readInt();
        System.out.print("ID Curso: ");
        Long courseId = (long) readInt();

        EnrollmentDTO created = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("✅ Matrícula exitosa. ID: " + created.getId());
    }

    private void findEnrollmentById() {
        System.out.print("ID Matrícula: ");
        Long id = (long) readInt();
        EnrollmentDTO enrollment = enrollmentService.findById(id);
        System.out.println("Encontrada: ID " + enrollment.getId() + " - Estado: " + enrollment.getStatus());
    }

    private void listAllEnrollments() {
        var enrollments = enrollmentService.findAll();
        if (enrollments.isEmpty()) {
            System.out.println("No hay matrículas.");
            return;
        }
        enrollments.forEach(e -> System.out.println("[" + e.getId() + "] Estudiante: " + e.getStudentId() + " | Curso: " + e.getCourseId()));
    }

    private void cancelEnrollment() {
        System.out.print("ID Matrícula a cancelar: ");
        Long id = (long) readInt();
        enrollmentService.cancelEnrollment(id);
        System.out.println("✅ Matrícula cancelada.");
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}