package com.example.web.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.web.entity.Course;
import com.example.web.repository.CourseRepository;

@Controller
public class StatisticsController {

    private final CourseRepository courseRepository;

    public StatisticsController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // рейтинг популярных направлений (count by direction)
    @GetMapping("/statistics")
    public String showStats(Model model) {
        // группируем курсы по direction
        var courses = courseRepository.findAll();
        List<Map.Entry<String, Long>> counts = courses.stream()
                .collect(Collectors.groupingBy(Course::getDirection, Collectors.counting()))
                //Подсчитываем количество курсов в каждой группе
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                //Сортируем по количеству курсов в убывающем порядке
                .toList();

        model.addAttribute("directionCounts", counts);
        // templates/statistics.html
        return "statistics";
    }
}
