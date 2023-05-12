package com.example.backend.controllers;

import com.example.backend.entity.Team;
import com.example.backend.entity.User;
import com.example.backend.entity.UserDetails;
import com.example.backend.repository.TeamRepo;
import com.example.backend.repository.UserRepo;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class UserController {
    private final UserRepo userRepo;
    private final TeamRepo teamRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepo userRepo, TeamRepo teamRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest request) {
        if (!request.password().equals(request.passwordConfirmation())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (request.email().equals("danielManager@gmail.com") || request.email().equals("lukaszManager@gmail.com") || request.email().equals("kasiaManager@gmail.com")) {
            Team team = teamRepo.findByName("Manager");
            User user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.MANAGER,team));
            String userJsonString = new Gson().toJson(user);
            return new ResponseEntity<>(userJsonString, HttpStatus.OK);
        }

        if (userRepo.findByEmail(request.email()) != null) {
            return new ResponseEntity<>("This email is used by existing account", HttpStatus.BAD_REQUEST);
        }

        switch (request.email()) {
            case "dawid@gmail.com", "michal@gmail.com", "karol@gmail.com", "adam@gmail.com", "julian@gmail.com", "franciszek@gmail.com", "szymon@gmail.com", "robert@gmail.com" -> {
                Team team = teamRepo.findByName("Alfa");
                User user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                String userJsonString = new Gson().toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
            case "dominik@gmail.com", "marcin@gmail.com", "kacper@gmail.com", "aleksander@gmail.com", "jonasz@gmail.com", "feliks@gmail.com", "samuel@gmail.com", "rafal@gmail.com" -> {
                Team team = teamRepo.findByName("Beta");
                User user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER,team));
                String userJsonString = new Gson().toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
            case "damian@gmail.com", "mateusz@gmail.com", "kamil@gmail.com", "adrian@gmail.com", "joachim@gmail.com", "fabian@gmail.com", "sebastian@gmail.com", "remigiusz@gmail.com" -> {
                Team team = teamRepo.findByName("Gamma");
                User user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                String userJsonString = new Gson().toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
            case "dariusz@gmail.com", "marek@gmail.com", "krzysztof@gmail.com", "antoni@gmail.com", "jozef@gmail.com", "filip@gmail.com", "salomon@gmail.com", "radoslaw@gmail.com" -> {
                Team team = teamRepo.findByName("Delta");
                User user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                String userJsonString = new Gson().toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("We are sorry but you are not one of our employees", HttpStatus.BAD_REQUEST);
    }

    private record RegistrationRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 64, message = "2")
            String name,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 64, message = "2")
            String surname,

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String password,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
                    "4")
            String passwordConfirmation) {
    }

    @GetMapping("/user")
    ResponseEntity<String> getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toArray()[0].toString();

        return new ResponseEntity<>("{\"username\": \"" + username + "\", \"role\": \"" + role + "\"}", HttpStatus.OK);
    }
}