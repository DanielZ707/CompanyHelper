package com.example.backend.security;

import com.example.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepo userRepo;

    @Autowired
    public SecurityConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

}
