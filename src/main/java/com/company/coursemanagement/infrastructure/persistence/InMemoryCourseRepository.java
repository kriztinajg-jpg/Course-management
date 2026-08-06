package com.company.coursemanagement.infrastructure.persistence;

import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCourseRepository implements CourseRepository {
    private final Map<Long, Course> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Course save(Course course) {
        if (course.getId() == null) {
            course.setId(idGenerator.getAndIncrement());
        }
        storage.put(course.getId(), course);
        return course;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Course> findAll() {
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
}