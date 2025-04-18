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

@Controller //Spring MVC контроллер, который обрабатывает HTTP-запросы и возвращает представления (views).
@RequestMapping("/courses")
//задаёт базовый URL для всех методов контроллера. Все методы будут доступны по адресам, начинающимся с /courses.
public class CourseController {

    private final CourseRepository courseRepository;
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Показать все курсы
    @GetMapping /* обрабатывает GET-запросы на /courses */
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll()); /* получает все курсы из базы данных. */
        return "courses"; // templates/courses.html
    }

    // Поиск курсов по названию
    @GetMapping("/search") /*  обрабатывает GET-запросы на /courses/search. */
    public String searchCourses(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("courses", courseRepository.findByTitleContainingIgnoreCase(
                keyword));
                /* метод репозитория, который ищет курсы, содержащие keyword в названии (без учёта регистра). */
        return "courses";// возвращаем ту же страницу, но с отфильтрованным списком
    }

    // Форма добавления (GET)
    @GetMapping("/add") // обрабатывает GET-запросы на /courses/add.
    public String showAddForm(Model model) {
        model.addAttribute("course", new Course());
        //Создаёт новый пустой объект Course и добавляет его в модель.
        return "course-form"; // отдельная страница с формой
    }

    // Обработка формы (POST)
    @PostMapping("/add") //обрабатывает POST-запросы на /courses/add.
    public String addCourse(@ModelAttribute("course") Course course) {//связывает данные формы с объектом Course.
        courseRepository.save(course);// сохраняет курс в базу данных.
        return "redirect:/courses";//перенаправляет на список всех курсов после сохранения.
    }

    // Удалить курс
    @GetMapping("/delete/{id}") //обрабатывает GET-запросы на /courses/delete/{id}
    public String deleteCourse(@PathVariable("id") Long id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }
}
