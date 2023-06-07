package com.example.backend.controllers;

import com.example.backend.models.entity.Construction;
import com.example.backend.models.entity.Team;
import com.example.backend.models.repository.ConstructionRepo;
import com.example.backend.models.repository.TeamRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Service
public class ConstructionController {

    private final ConstructionRepo constructionRepo;


    private final Gson gsonParser = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().serializeNulls().create();

    private final TeamRepo teamRepo;

    @Autowired
    public ConstructionController(ConstructionRepo constructionRepo, TeamRepo teamRepo) {

        this.constructionRepo = constructionRepo;
        this.teamRepo = teamRepo;
    }

    @PostMapping("/addConstruction")
    public ResponseEntity<String> addConstruction(@Valid @RequestBody ConstructionController.ConstructionRequest request) {
        if (constructionRepo.findByName(request.name()) != null) {
            return new ResponseEntity<>("This construction has already existed", HttpStatus.BAD_REQUEST);
        } else {
            Construction construction = constructionRepo.save(new Construction(request.name(), request.town(), request.street(), request.buildingNumber(), request.progress(), request.dateOfBegging(), request.deadlineDay()));
            String constructionJsonString = gsonParser.toJson(construction);
            return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
        }
    }

    @GetMapping("/allConstructions")
    public ResponseEntity<String> allConstructions() {
        List<Construction> constructions = constructionRepo.findAll();
        String constructionJsonString = gsonParser.toJson(constructions);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }

    @PostMapping("/oneConstruction")
    public ResponseEntity<String> oneConstructions(@Valid @RequestBody ConstructionController.ConstructionRequestName requestName){
        Construction construction = constructionRepo.findByName(requestName.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        String constructionJsonString = gsonParser.toJson(construction);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }

    @PostMapping("/assignToCon")
    public ResponseEntity<String> assignToCon(@Valid @RequestBody ConstructionController.TeamRequestAssignToCon request) {
        Construction construction = constructionRepo.findByName(request.nameCon());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        Team team = teamRepo.findByName(request.name());
        if (team == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        teamRepo.changeConstruction(request.name, construction.getIdConstruction());
        team = teamRepo.findByName(request.name());
        String teamJsonString = gsonParser.toJson(team);
        System.out.println(teamJsonString);
        return new ResponseEntity<>(teamJsonString, HttpStatus.OK);
    }


    @PostMapping("/deleteConstruction")
    public ResponseEntity<String> deleteConstructions(@Valid @RequestBody ConstructionController.ConstructionRequestName requestName) {
        Construction construction = constructionRepo.findByName(requestName.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        constructionRepo.deleteConstruction(requestName.name());
        String constructionJsonString = gsonParser.toJson(construction);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }

    @PostMapping("/nameConstruction")
    public ResponseEntity<String> nameConstruction(@Valid @RequestBody ConstructionController.ConstructionRequestChangeName requestChangeName) {
        Construction construction = constructionRepo.findByName(requestChangeName.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        constructionRepo.changeConstructionName(requestChangeName.name(), requestChangeName.newName());
        construction = constructionRepo.findByName(requestChangeName.newName());
        String constructionJsonString = gsonParser.toJson(construction);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }

    @PostMapping("/progressConstruction")
    public ResponseEntity<String> progressConstruction(@Valid @RequestBody ConstructionController.ConstructionRequestChangeProgress requestChangeProgress) {
        Construction construction = constructionRepo.findByName(requestChangeProgress.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        constructionRepo.changeConstructionProgress(requestChangeProgress.name(), requestChangeProgress.newProgress());
        construction = constructionRepo.findByName(requestChangeProgress.name());
        String constructionJsonString = gsonParser.toJson(construction);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }

    @PostMapping("/deadlineConstruction")
    public ResponseEntity<String> deadlineConstruction(@Valid @RequestBody ConstructionController.ConstructionRequestDeadline requestDeadline) {
        Construction construction = constructionRepo.findByName(requestDeadline.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        constructionRepo.changeConstructionDeadline(requestDeadline.name(), requestDeadline.newDeadline());
        construction = constructionRepo.findByName(requestDeadline.name());
        String constructionJsonString = gsonParser.toJson(construction);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }


    private record ConstructionRequest(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String town,

            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String street,
            @NotNull(message = "0")
            @Min(1)
            int buildingNumber,

            @NotNull(message = "0")
            int progress,

            Date dateOfBegging,
            Date deadlineDay) {

    }

    private record ConstructionRequestName(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name) {

    }

    private record ConstructionRequestChangeProgress(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name,
            @NotNull(message = "0")

            int newProgress) {

    }

    private record ConstructionRequestChangeName(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name,
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String newName) {

    }

    private record ConstructionRequestDeadline(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name,
            Date newDeadline) {

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
