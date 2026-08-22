package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.application.service.StudentService;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDTO create(StudentDTO dto) {
        validateNullOrEmptyFields(dto);
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
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        validateNullOrEmptyFields(dto);

        if (!existingStudent.getEmail().equalsIgnoreCase(dto.getEmail())
                && studentRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email '" + dto.getEmail() + "' ya pertenece a otro estudiante.");
        }

        boolean nameChanged = !existingStudent.getFirstName().equalsIgnoreCase(dto.getFirstName())
                || !existingStudent.getLastName().equalsIgnoreCase(dto.getLastName());

        if (nameChanged && studentRepository.existsByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())) {
            throw new IllegalArgumentException("Ya existe otro estudiante registrado como: "
                    + dto.getFirstName() + " " + dto.getLastName());
        }

        if (dto.getBirthDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser una fecha futura.");
        }

        existingStudent.setFirstName(dto.getFirstName().trim());
        existingStudent.setLastName(dto.getLastName().trim());
        existingStudent.setEmail(dto.getEmail().trim().toLowerCase());
        existingStudent.setBirthDate(dto.getBirthDate());

        Student updated = studentRepository.save(existingStudent);
        return toDTO(updated);
    }

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