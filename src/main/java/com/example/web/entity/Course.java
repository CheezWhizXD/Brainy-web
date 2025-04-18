package com.example.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // аннотация JPA, указывающая, что этот класс является сущностью базы данных
@Table(name = "courses") //указывает имя таблицы в БД
public class Course {
    @Id //помечает поле как первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название курса
    @Column(nullable = false) // указывает, что поле не может быть NULL в БД
    private String title;

    // Преподаватель
    private String teacher;

    // Количество уроков
    private Integer lessonsCount;

    // Цена
    private Double price;

    // Направление (например, "Программирование", "Дизайн" и т.п.)
    private String direction;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTeacher() { return teacher; }
    public void setTeacher(String teacher) { this.teacher = teacher; }

    public Integer getLessonsCount() { return lessonsCount; }
    public void setLessonsCount(Integer lessonsCount) { this.lessonsCount = lessonsCount; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
}
