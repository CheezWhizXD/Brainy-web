package com.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.web.repository.UserRepository;

/*
 Конфигурация безопасности для Spring Security.
 Настройки безопасности включают в себя конфигурацию для аутентификации пользователей, настройки для логина,
 а также определения доступа к статическим ресурсам и страницам.
 */
@Configuration
public class SecurityConfig {

    private final UserRepository userRepository; //UserRepository - это интерфейс Spring Data JPA для работы с пользователями в базе данных.

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // Бин для кодирования паролей
        return new BCryptPasswordEncoder(); //BCrypt - это алгоритм хеширования, специально разработанный для хранения паролей.
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Анонимный класс, загружающий пользователя из БД
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                var user = userRepository.findByUsername(username);
                if (user == null) { //
                    throw new UsernameNotFoundException("Пользователь не найден: " + username);
                    //Если пользователь не найден - выбрасывает исключение


                }
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().replace("ROLE_", ""))
                        .build();
                        //Если найден - создаёт объект UserDetails с:1.Именем пользователя 2.Паролем (уже закодированным)3.Ролями (удаляется префикс "ROLE_", так как Spring Security добавляет его автоматически)
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/about", "/statistics", "/login", "/error", "/css/**", "/images/**").permitAll()
                .requestMatchers("/courses", "/courses/search").permitAll()
                // добавление/удаление курса - только авторизованным
                .requestMatchers("/courses/add", "/courses/delete/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login") // Страница логина
                .permitAll() // Разрешить доступ к странице логина всем
                .defaultSuccessUrl("/")
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
            );

        // http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
