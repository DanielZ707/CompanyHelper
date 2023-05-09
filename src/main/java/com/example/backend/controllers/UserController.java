package com.example.backend.controllers;

import com.example.backend.entity.UserEntityDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
    @GetMapping("/user")
    ResponseEntity<String> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntityDetails userEntityDetails = (UserEntityDetails) authentication.getPrincipal();
        String username = userEntityDetails.getUsername();
        String role = userEntityDetails.getAuthorities().toArray()[0].toString();

        return new ResponseEntity<>("{\"username\": \"" + username + "\", \"role\": \"" + role + "\"}", HttpStatus.OK);
    }
}
