package com.example.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // аннотация JPA, указывающая, что этот класс является сущностью базы данных
@Table(name = "users") //указывает имя таблицы в БД
public class User {
    @Id //помечает поле как первичный ключ
    @GeneratedValue(strategy = GenerationType.IDENTITY) //база данных генерирует автоинкремент
    private Long id;

    // Логин (уникальный)
    @Column(unique = true, nullable = false)
    private String username;

    // Хэшированный пароль
    @Column(nullable = false)
    private String password;

    // Роль пользователя (ROLE_USER, ROLE_ADMIN)
    @Column(nullable = false)
    private String role;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
