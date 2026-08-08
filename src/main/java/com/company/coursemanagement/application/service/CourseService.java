package com.company.coursemanagement.application.service;

import com.company.coursemanagement.application.dto.CourseDTO;
import java.util.List;

public interface CourseService {
    CourseDTO create(CourseDTO dto);
    CourseDTO findById(Long id);
    List<CourseDTO> findAll();
    CourseDTO update(Long id, CourseDTO dto);
    void delete(Long id);
}