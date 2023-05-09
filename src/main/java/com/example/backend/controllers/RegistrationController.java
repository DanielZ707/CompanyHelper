package com.example.backend.controllers;

import com.example.backend.entity.UserEntity;
import com.example.backend.repository.UserRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
public class RegistrationController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepo userRepo, PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest request) {

        if (!request.password().equals(request.passwordConfirmation())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (request.email().equals("danielManager@gmail.com") || request.email().equals("lukaszManager@gmail.com") || request.email().equals("kasiaManager@gmail.com")) {
            userRepo.save(new UserEntity(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), UserEntity.Role.MANAGER, UserEntity.Team.MANAGER));
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }

        if (userRepo.findByEmail(request.email()) != null) {
            return new ResponseEntity<>("This email is used by existing account", HttpStatus.BAD_REQUEST);
        }

        if (request.email().equals("dawid@gmail.com") || request.email().equals("michal@gmail.com") || request.email().equals("karol@gmail.com") || request.email().equals("adam@gmail.com") || request.email().equals("julian@gmail.com") || request.email().equals("franciszek@gmail.com") || request.email().equals("szymon@gmail.com") || request.email().equals("robert@gmail.com")) {
            userRepo.save(new UserEntity(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), UserEntity.Role.WORKER, UserEntity.Team.ALFA));
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } else if (request.email().equals("dominik@gmail.com") || request.email().equals("marcin@gmail.com") || request.email().equals("kacper@gmail.com") || request.email().equals("aleksander@gmail.com") || request.email().equals("jonasz@gmail.com") || request.email().equals("feliks@gmail.com") || request.email().equals("samuel@gmail.com") || request.email().equals("rafal@gmail.com")) {
            userRepo.save(new UserEntity(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), UserEntity.Role.WORKER, UserEntity.Team.BETA));
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } else if (request.email().equals("damian@gmail.com") || request.email().equals("mateusz@gmail.com") || request.email().equals("kamil@gmail.com") || request.email().equals("adrian@gmail.com") || request.email().equals("joachim@gmail.com") || request.email().equals("fabian@gmail.com") || request.email().equals("sebastian@gmail.com") || request.email().equals("remigiusz@gmail.com")) {
            userRepo.save(new UserEntity(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), UserEntity.Role.WORKER, UserEntity.Team.GAMMA));
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } else if (request.email().equals("dariusz@gmail.com") || request.email().equals("marek@gmail.com") || request.email().equals("krzysztof@gmail.com") || request.email().equals("antoni@gmail.com") || request.email().equals("jozef@gmail.com") || request.email().equals("filip@gmail.com") || request.email().equals("salomon@gmail.com") || request.email().equals("radoslaw@gmail.com")) {
            userRepo.save(new UserEntity(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), UserEntity.Role.WORKER, UserEntity.Team.DELTA));
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
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
}