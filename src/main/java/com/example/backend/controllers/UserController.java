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

import java.util.List;

@RestController
@Service
public class UserController {
    private final UserRepo userRepo;
    private final TeamRepo teamRepo;
    private final PasswordEncoder passwordEncoder;

    private User user = new User();
    private Team team = new Team();

    private final Gson gsonParser = new Gson();

    private String userJsonString;

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

        if (userRepo.findByEmail(request.email()) != null) {
            return new ResponseEntity<>("This email is used by existing account", HttpStatus.BAD_REQUEST);
        }

        if (request.email().equals("danielAdmin@gmail.com")) {
            team = teamRepo.findByName("Admin");
            user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.ADMIN, team));
            team.getUsers().add(user);
            userJsonString = gsonParser.toJson(user);
            return new ResponseEntity<>(userJsonString, HttpStatus.OK);
        }

        if (request.email().equals("kingaManager@gmail.com") || request.email().equals("henrykManager@gmail.com") || request.email().equals("katarzynaManager@gmail.com")) {
            team = teamRepo.findByName("Manager");
            user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.MANAGER, team));
            team.getUsers().add(user);
            userJsonString = gsonParser.toJson(user);
            return new ResponseEntity<>(userJsonString, HttpStatus.OK);
        }


        switch (request.email()) {
            case "dawid@gmail.com", "michal@gmail.com", "karol@gmail.com", "adam@gmail.com", "julian@gmail.com", "franciszek@gmail.com", "szymon@gmail.com", "robert@gmail.com" -> {
                team = teamRepo.findByName("Alfa");
                user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                team.getUsers().add(user);
                userJsonString = gsonParser.toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
            case "dominik@gmail.com", "marcin@gmail.com", "kacper@gmail.com", "aleksander@gmail.com", "jonasz@gmail.com", "feliks@gmail.com", "samuel@gmail.com", "rafal@gmail.com" -> {
                team = teamRepo.findByName("Beta");
                user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                userJsonString = gsonParser.toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
            case "damian@gmail.com", "mateusz@gmail.com", "kamil@gmail.com", "adrian@gmail.com", "joachim@gmail.com", "fabian@gmail.com", "sebastian@gmail.com", "remigiusz@gmail.com" -> {
                team = teamRepo.findByName("Gamma");
                user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                team.getUsers().add(user);
                userJsonString = gsonParser.toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
            case "dariusz@gmail.com", "marek@gmail.com", "krzysztof@gmail.com", "antoni@gmail.com", "jozef@gmail.com", "filip@gmail.com", "salomon@gmail.com", "radoslaw@gmail.com" -> {
                team = teamRepo.findByName("Delta");
                user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), User.Role.WORKER, team));
                team.getUsers().add(user);
                userJsonString = gsonParser.toJson(user);
                return new ResponseEntity<>(userJsonString, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("We are sorry but you are not one of our employees", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/oneUser")
    public ResponseEntity<String> oneUser(@Valid @RequestBody RegistrationRequestEmail request) {
        user = userRepo.findByEmail(request.email());
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<String> allUsers() {
        List<User> users = userRepo.findAll();
        userJsonString = gsonParser.toJson(users);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/changeRole")
    public ResponseEntity<String> changeRole(@Valid @RequestBody RegistrationRequestRole request) {
        user = userRepo.findByEmail(request.email());
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        switch (request.role()) {
            case "Manager" -> user.setRole(User.Role.MANAGER);
            case "Admin" -> user.setRole(User.Role.ADMIN);
            case "Worker" -> user.setRole(User.Role.WORKER);
        }
        user = userRepo.findByEmail(request.email());
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/changeTeam")
    public ResponseEntity<String> changeTeam(@Valid @RequestBody RegistrationRequestTeam request) {
        team = teamRepo.findByName(request.teamName());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        user = userRepo.findByEmail(request.email());
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        user.setTeam(team);
        user = userRepo.findByEmail(request.email());
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody RegistrationRequestEmail request) {
        user = userRepo.findByEmail(request.email());
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        userRepo.deleteAccount(request.email());
        team.getUsers().remove(user);
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
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

    private record RegistrationRequestEmail(

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email) {
    }

    private record RegistrationRequestTeam(

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email,
            String teamName) {
    }

    private record RegistrationRequestRole(

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email,
            String role) {
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