package com.company.coursemanagement.infrastructure.persistence;

import com.company.coursemanagement.domain.model.Enrollment;
import com.company.coursemanagement.domain.model.EnrollmentStatus;
import com.company.coursemanagement.domain.repository.EnrollmentRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryEnrollmentRepository implements EnrollmentRepository {
    private final Map<Long, Enrollment> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Enrollment save(Enrollment enrollment) {
        if (enrollment.getId() == null) {
            enrollment.setId(idGenerator.getAndIncrement());
        }
        storage.put(enrollment.getId(), enrollment);
        return enrollment;
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Enrollment> findAll() {
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
    public long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status) {
        return storage.values().stream()
                .filter(e -> e.getCourseId().equals(courseId) && e.getStatus() == status)
                .count();
    }
}