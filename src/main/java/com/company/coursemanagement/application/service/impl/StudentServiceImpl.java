package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;
import java.time.LocalDate;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        Student student = new Student(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBirthDate());
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    @Override
    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return toDTO(student);
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public StudentDTO update(Long id, StudentDTO dto) {
        // 1. VERIFICACIÓN DE EXISTENCIA
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // 2. VERIFICACIÓN DE CAMPOS NULOS O VACÍOS
        validateNullOrEmptyFields(dto);

        // 3. VERIFICACIÓN DE EMAIL ÚNICO
        // Si cambió el correo, verificamos que no lo tenga otro estudiante
        if (!existingStudent.getEmail().equalsIgnoreCase(dto.getEmail())
                && studentRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email '" + dto.getEmail() + "' ya pertenece a otro estudiante.");
        }

        // 4. VERIFICACIÓN DE NOMBRE Y APELLIDO ÚNICOS
        // Si cambió el nombre o el apellido, verificamos que no exista otra persona con la misma combinación
        boolean nameChanged = !existingStudent.getFirstName().equalsIgnoreCase(dto.getFirstName())
                || !existingStudent.getLastName().equalsIgnoreCase(dto.getLastName());

        if (nameChanged && studentRepository.existsByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())) {
            throw new IllegalArgumentException("Ya existe otro estudiante registrado como: "
                    + dto.getFirstName() + " " + dto.getLastName());
        }

        // 5. VERIFICACIÓN DE FECHA DE NACIMIENTO
        if (dto.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura.");
        }

        // 6. ACTUALIZACIÓN Y LIMPIEZA DE DATOS (trim para quitar espacios extra)
        existingStudent.setFirstName(dto.getFirstName().trim());
        existingStudent.setLastName(dto.getLastName().trim());
        existingStudent.setEmail(dto.getEmail().trim().toLowerCase());
        existingStudent.setBirthDate(dto.getBirthDate());

        // 7. GUARDAR Y RETORNAR
        Student updated = studentRepository.save(existingStudent);
        return toDTO(updated);
    }

    // Métodos auxiliares para mantener el código limpio:
    private void validateNullOrEmptyFields(StudentDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos del estudiante no pueden ser nulos.");
        }
        if (dto.getFirstName() == null || dto.getFirstName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (dto.getLastName() == null || dto.getLastName().isBlank()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (dto.getBirthDate() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
    }
    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private StudentDTO toDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getBirthDate());
    }
}