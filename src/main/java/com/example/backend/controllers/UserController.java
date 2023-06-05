package com.example.backend.controllers;

import com.example.backend.models.entity.*;
import com.example.backend.models.repository.ConstructionRepo;
import com.example.backend.models.repository.TeamRepo;
import com.example.backend.models.repository.TokenRepository;
import com.example.backend.models.repository.UserRepo;
import com.example.backend.security.JwtService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Service
public class UserController {
    private final UserRepo userRepo;
    private final TeamRepo teamRepo;

    private final ConstructionRepo constructionRepo;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    private User user = new User();
    private Team team = new Team();

    private final Gson gsonParser = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

    private String userJsonString;

    private final JwtService jwtService;

    @Autowired
    public UserController(UserRepo userRepo, TeamRepo teamRepo, ConstructionRepo constructionRepo, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
        this.constructionRepo = constructionRepo;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationRequest request) {

        if (!request.password().equals(request.passwordConfirmation())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (userRepo.findByEmail(request.email()).isPresent()) {
            return new ResponseEntity<>("This email is used by existing account", HttpStatus.BAD_REQUEST);
        }

        if (request.email().equals("danielAdmin@gmail.com")) {
            team = teamRepo.findByName("Admin");
            user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), request.telephone(), request.job(), User.Role.ADMIN, team));
            team.getUsers().add(user);
            userJsonString = gsonParser.toJson(user);
            var jwtToken = jwtService.generateToken(new UserDetails(user));
            var refreshToken = jwtService.generateRefreshToken(new UserDetails(user));
            saveUserToken(user, jwtToken);
            return new ResponseEntity<>(userJsonString, HttpStatus.OK);
        }

        if (request.email().equals("kingaManager@gmail.com") || request.email().equals("henrykManager@gmail.com") || request.email().equals("katarzynaManager@gmail.com")) {
            team = teamRepo.findByName("Manager");
            user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), request.telephone(), request.job(), User.Role.MANAGER, team));
            team.getUsers().add(user);
            userJsonString = gsonParser.toJson(user);
            var jwtToken = jwtService.generateToken(new UserDetails(user));
            var refreshToken = jwtService.generateRefreshToken(new UserDetails(user));
            saveUserToken(user, jwtToken);
            return new ResponseEntity<>(userJsonString, HttpStatus.OK);
        }
        team = teamRepo.findByName(request.team());
        user = userRepo.save(new User(request.name(), request.surname(), request.email(), passwordEncoder.encode(request.password()), request.telephone(), request.job(), User.Role.WORKER, team));
        assert team.getUsers() != null;
        team.getUsers().add(user);
        userJsonString = gsonParser.toJson(user);
        var jwtToken = jwtService.generateToken(new UserDetails(user));
        var refreshToken = jwtService.generateRefreshToken(new UserDetails(user));
        saveUserToken(user, jwtToken);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);

    }


    @PostMapping("/oneUser")
    public ResponseEntity<String> oneUser(@Valid @RequestBody requestEmail request) {
        user = userRepo.findByEmail(request.email()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/allUsers")
    public ResponseEntity<String> allUsers(@RequestBody(required = false) requestTeamFilter request) {
        List<User> users = userRepo.findAll();
        List<String> filter;
        List<Integer> integers = new ArrayList<>();
        System.out.println(request.filterString);
        if (request.filterString == null) {
            integers.add(users.size());
            integers.add(0);
        } else {
            filter = Arrays.asList(request.filterString.split(","));
            if (filter.size() != 2) {
                integers.add(users.size());
                integers.add(0);
            } else {
                for (String param : filter) {
                    integers.add(Integer.parseInt(param));
                }
            }
        }

        if (integers.get(0) < 0 || integers.get(1) < 0) {
            return new ResponseEntity<>("Bad parameters!", HttpStatus.BAD_REQUEST);
        }
        if (integers.get(1) > (users.size() - 1)) {
            integers.set(1, users.size() - 1);
            integers.set(0, 0);
        } else if (integers.get(0) > (users.size() - integers.get(1))) {
            integers.set(0, users.size() - integers.get(1));
        }
        List<User> updatedUsers = new ArrayList<>();
        for (int i = integers.get(1); i < (integers.get(0) + integers.get(1)); i++) {
            updatedUsers.add(users.get(i));
        }
        userJsonString = gsonParser.toJson(updatedUsers);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/changeRole")
    public ResponseEntity<String> changeRole(@Valid @RequestBody requestRole request) {
        user = userRepo.findByEmail(request.email()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        switch (request.role()) {
            case "Manager" -> user.setRole(User.Role.MANAGER);
            case "Admin" -> user.setRole(User.Role.ADMIN);
            case "Worker" -> user.setRole(User.Role.WORKER);
        }
        user = userRepo.findByEmail(request.email()).orElse(null);
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/changeTeam")
    public ResponseEntity<String> changeTeam(@Valid @RequestBody requestTeam request) {
        team = teamRepo.findByName(request.teamName());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        user = userRepo.findByEmail(request.email()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        user.setTeam(team);
        user = userRepo.findByEmail(request.email()).orElse(null);
        userJsonString = gsonParser.toJson(user);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/teamUsers")
    public ResponseEntity<String> teamUsers(@Valid @RequestBody requestTeamName request) {
        team = teamRepo.findByName(request.teamName());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        List<User> users = userRepo.findUsersFromTeam(team.getIdTeam());
        userJsonString = gsonParser.toJson(users);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/teamUsersCon")
    public ResponseEntity<String> teamUsers(@Valid @RequestBody requestTeamNameCon request) {
        Construction construction = constructionRepo.findByName(request.conName());
        System.out.println(construction);
        team = teamRepo.findByIdCon(construction.getIdConstruction());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        List<User> users = userRepo.findUsersFromTeam(team.getIdTeam());
        userJsonString = gsonParser.toJson(users);
        return new ResponseEntity<>(userJsonString, HttpStatus.OK);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@Valid @RequestBody requestEmail request) {
        user = userRepo.findByEmail(request.email()).orElse(null);
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
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message = "4")
            String password,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message = "4")
            String passwordConfirmation,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 11, message = "2")
            String telephone,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 15, message = "2")
            String job,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 15, message = "2")
            String team) {
    }

    private record requestEmail(

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email) {
    }

    private record requestTeam(

            @Email(message = "3")
            @Pattern(regexp = ".+@.+\\..+", message = "3")
            @NotBlank(message = "1")
            @NotNull(message = "0")
            @Size(min = 3, max = 64, message = "2")
            String email,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 30, message = "2")
            String teamName) {
    }

    private record requestTeamNameCon(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 30, message = "2")
            String conName) {
    }

    private record requestTeamFilter(

            String filterString) {
    }

    private record requestTeamName(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 30, message = "2")
            String teamName) {
    }

    private record requestRole(

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