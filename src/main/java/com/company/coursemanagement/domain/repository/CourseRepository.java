package com.company.coursemanagement.domain.repository;

import com.company.coursemanagement.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}