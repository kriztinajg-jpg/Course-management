package com.company.coursemanagement.infrastructure.persistence;

import com.company.coursemanagement.domain.model.Student;
import com.company.coursemanagement.domain.repository.StudentRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<Long, Student> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idGenerator.getAndIncrement());
        }
        storage.put(student.getId(), student);
        return student;
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return storage.values().stream()
                .anyMatch(student -> student.getEmail() != null
                        && student.getEmail().equalsIgnoreCase(email.trim()));
    }

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null) return false;
        return storage.values().stream()
                .anyMatch(student ->
                        student.getFirstName() != null && student.getFirstName().equalsIgnoreCase(firstName.trim())
                                && student.getLastName() != null && student.getLastName().equalsIgnoreCase(lastName.trim())
                );
    }
}