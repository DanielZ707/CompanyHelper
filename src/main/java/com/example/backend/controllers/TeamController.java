package com.example.backend.controllers;

import com.example.backend.entity.Construction;
import com.example.backend.entity.Team;
import com.example.backend.repository.ConstructionRepo;
import com.example.backend.repository.TeamRepo;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Service
public class TeamController {

    private final TeamRepo teamRepo;
    private final ConstructionRepo constructionRepo;

    @Autowired
    public TeamController(TeamRepo teamRepo, ConstructionRepo constructionRepo) {

        this.teamRepo = teamRepo;
        this.constructionRepo = constructionRepo;
    }

    @PostMapping("/addTeam")
    public ResponseEntity<String> addTeam(@Valid @RequestBody TeamController.TeamRequest request) {

        if (teamRepo.findByName(request.name()) != null) {
            return new ResponseEntity<>("This team has already existed", HttpStatus.BAD_REQUEST);
        } else {
            Team team = teamRepo.save(new Team(request.name()));
            String teamJsonString = new Gson().toJson(team);
            return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
        }
    }

    @GetMapping("/allTeams")
    public ResponseEntity<String> allTeams() {
        List<Team> teams = teamRepo.findAll();
        String teamsJsonString = new Gson().toJson(teams);
        return new ResponseEntity<>(teamsJsonString, HttpStatus.OK);
    }

    @GetMapping("/oneTeam")
    public ResponseEntity<String> oneTeam(@Valid @RequestBody TeamController.TeamRequest request) {
        Team team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        String teamJsonString = new Gson().toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/deleteTeam")
    public ResponseEntity<String> deleteTeam(@Valid @RequestBody TeamController.TeamRequest request) {
        Team team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamRepo.deleteTeam(request.name());
        String teamJsonString = new Gson().toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/nameTeam")
    public ResponseEntity<String> changeTeamName(@Valid @RequestBody TeamController.TeamRequestChangeName request) {
        Team team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamRepo.changeTeamName(request.name(), request.newName());
        team = teamRepo.findByName(request.newName());
        String teamJsonString = new Gson().toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/assignToCon")
    public ResponseEntity<String> assignToCon(@Valid @RequestBody TeamController.TeamRequestAssignToCon request) {
        Construction construction = constructionRepo.findByName(request.nameCon());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        Team team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        team.setConstruction(construction);
        team = teamRepo.findByName(request.name());
        String teamJsonString = new Gson().toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }


    private record TeamRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name) {

    }


    private record TeamRequestChangeName(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String newName) {

    }

    private record TeamRequestAssignToCon(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String nameCon) {

    }

}