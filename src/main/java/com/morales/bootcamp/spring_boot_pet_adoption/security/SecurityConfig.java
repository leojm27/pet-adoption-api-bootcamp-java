package com.morales.bootcamp.spring_boot_pet_adoption.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF permite solicitudes de cualquier origen
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated() // Todas las solicitudes deben estar autenticadas
                )
                .httpBasic(customizer -> customizer.realmName("MyAppRealm")); // Habilita autenticación básica

        return http.build();
    }
}
