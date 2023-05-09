package com.example.backend.security;

import com.example.backend.entity.UserEntity;
import com.example.backend.entity.UserEntityDetails;
import com.example.backend.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Date;

public class LoginHandler implements AuthenticationSuccessHandler {
    private final UserRepo userRepo;

    public LoginHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        UserEntityDetails user = (UserEntityDetails) authentication.getPrincipal();
        UserEntity currentUser = userRepo.findByEmail(user.getEmail());
        userRepo.changeLoginDate(currentUser.getIdUser(), new Date());
    }
}
