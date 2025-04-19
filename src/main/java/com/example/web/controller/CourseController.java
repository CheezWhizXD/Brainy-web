package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.entity.Course;
import com.example.web.repository.CourseRepository;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // ===== Список курсов =====
    @GetMapping
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses";
    }

    // ===== Поиск =====
    @GetMapping("/search")
    public String searchCourses(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("courses",
                courseRepository.findByTitleContainingIgnoreCase(keyword));
        return "courses";
    }

    // ===== Добавление курса =====
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("isEdit", false);   // флаг шаблону
        return "course-form";
    }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return "redirect:/courses";
    }

    // ===== Удаление =====
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }


    // Форма редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Курс с id %d не найден".formatted(id)));
        model.addAttribute("course", course);
        model.addAttribute("isEdit", true);
        return "course-form";
    }

    // Обработка формы
    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable("id") Long id,
                               @ModelAttribute("course") Course updated) {

        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Курс с id %d не найден".formatted(id)));

        existing.setTitle(updated.getTitle());
        existing.setTeacher(updated.getTeacher());
        existing.setLessonsCount(updated.getLessonsCount());
        existing.setPrice(updated.getPrice());
        existing.setDirection(updated.getDirection());

        courseRepository.save(existing);
        return "redirect:/courses";
    }
}
