package com.company.coursemanagement.application.service.impl;

import com.company.coursemanagement.application.dto.CourseDTO;
import com.company.coursemanagement.application.service.CourseService;
import com.company.coursemanagement.domain.exception.CourseNotFoundException;
import com.company.coursemanagement.domain.model.Course;
import com.company.coursemanagement.domain.repository.CourseRepository;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public CourseDTO create(CourseDTO dto) {
        Course course = new Course(dto.getId(), dto.getCode(), dto.getName(), dto.getDescription(), dto.getMaxCapacity());
        Course saved = courseRepository.save(course);
        return toDTO(saved);
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return toDTO(course);
    }

    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        Course course = new Course(id, dto.getCode(), dto.getName(), dto.getDescription(), dto.getMaxCapacity());
        Course updated = courseRepository.save(course);
        return toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    private CourseDTO toDTO(Course course) {
        return new CourseDTO(course.getId(), course.getCode(), course.getName(), course.getDescription(), course.getMaxCapacity());
    }
}