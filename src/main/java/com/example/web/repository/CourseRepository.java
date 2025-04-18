package com.example.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // поиск по названию
    List<Course> findByTitleContainingIgnoreCase(String title);
}
