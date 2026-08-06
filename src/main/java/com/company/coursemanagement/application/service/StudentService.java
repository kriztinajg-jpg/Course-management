package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.StudentDTO;
import com.company.coursemanagement.domain.exception.StudentNotFoundException;
import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO create(StudentDTO dto) {
        Student student = new Student(dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBirthDate());
        Student saved = studentRepository.save(student);
        return toDTO(saved);
    }

    public StudentDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return toDTO(student);
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public StudentDTO update(Long id, StudentDTO dto) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        Student student = new Student(id, dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBirthDate());
        Student updated = studentRepository.save(student);
        return toDTO(updated);
    }

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