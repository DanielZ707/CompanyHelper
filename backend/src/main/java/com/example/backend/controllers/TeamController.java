package com.example.backend.controllers;

import com.example.backend.models.entity.Construction;
import com.example.backend.models.entity.Team;
import com.example.backend.models.repository.ConstructionRepo;
import com.example.backend.models.repository.TeamRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private Team team = new Team();

    private final Gson gsonParser = new GsonBuilder().setDateFormat("yyyy-MM-dd").serializeNulls().excludeFieldsWithoutExposeAnnotation().create();

    private String teamJsonString;

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
            team = teamRepo.save(new Team(request.name()));
            teamJsonString = gsonParser.toJson(team);
            return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
        }
    }

    @GetMapping("/allTeams")
    public ResponseEntity<String> allTeams() {
        List<Team> teams = teamRepo.findAll();
        teamJsonString = gsonParser.toJson(teams);
        System.out.println(teamJsonString);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/oneTeam")
    public ResponseEntity<String> oneTeam(@Valid @RequestBody TeamController.TeamRequest request) {
        team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamJsonString = gsonParser.toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/constructionTeam")
    public ResponseEntity<String> consTeam(@Valid @RequestBody TeamController.TeamRequest request) {
        Construction construction = constructionRepo.findByName(request.name);
        team = teamRepo.findByIdCon(construction.getIdConstruction());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamJsonString = gsonParser.toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/deleteTeam")
    public ResponseEntity<String> deleteTeam(@Valid @RequestBody TeamController.TeamRequest request) {
        team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamRepo.deleteTeam(request.name());
        teamJsonString = gsonParser.toJson(team);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }

    @PostMapping("/nameTeam")
    public ResponseEntity<String> changeTeamName(@Valid @RequestBody TeamController.TeamRequestChangeName request) {
        team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamRepo.changeTeamName(request.name(), request.newName());
        team = teamRepo.findByName(request.newName());
        teamJsonString = gsonParser.toJson(team);
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


}