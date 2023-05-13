package com.example.backend.controllers;

import com.example.backend.entity.Construction;
import com.example.backend.repository.ConstructionRepo;
import com.google.gson.Gson;
import jakarta.validation.Valid;
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

    @Autowired
    public ConstructionController(ConstructionRepo constructionRepo) {

        this.constructionRepo = constructionRepo;
    }

    @PostMapping("/addConstruction")
    public ResponseEntity<String> addConstruction(@Valid @RequestBody ConstructionController.ConstructionRequest request) {


        if (constructionRepo.findByName(request.name()) != null) {
            return new ResponseEntity<>("This construction has already existed", HttpStatus.BAD_REQUEST);
        } else {
            Construction construction = constructionRepo.save(new Construction(request.name(), request.town(), request.street(), request.buildingNumber(), request.dateOfBegging(), request.deadlineDay()));
            String constructionJsonString = new Gson().toJson(construction);
            return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
        }
    }

    @GetMapping("/allConstructions")
    public ResponseEntity<String> allConstructions() {
        List<Construction> constructions = constructionRepo.findAll();
        String constructionsJsonString = new Gson().toJson(constructions);
        return new ResponseEntity<>(constructionsJsonString, HttpStatus.OK);
    }

    @GetMapping("/oneConstruction")
    public ResponseEntity<String> oneConstructions(@Valid @RequestBody ConstructionController.ConstructionRequestName requestName) {
        Construction construction = constructionRepo.findByName(requestName.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        String constructionJsonString = new Gson().toJson(construction);
        return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
    }

    @PostMapping("/deleteConstruction")
    public ResponseEntity<String> deleteConstructions(@Valid @RequestBody ConstructionController.ConstructionRequestName requestName) {
        Construction construction = constructionRepo.findByName(requestName.name());
        if (construction == null) {
            return new ResponseEntity<>("No data found!", HttpStatus.BAD_REQUEST);
        }
        constructionRepo.deleteConstruction(requestName.name());
        String constructionJsonString = new Gson().toJson(construction);
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
        String constructionJsonString = new Gson().toJson(construction);
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
        String constructionJsonString = new Gson().toJson(construction);
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
            @NotBlank(message = "1")
            @Size(min = 1, message = "2")
            int buildingNumber,

            Date dateOfBegging,
            Date deadlineDay) {

    }

    private record ConstructionRequestName(
            @NotNull(message = "0")
            @NotBlank(message = "1")
            @Size(min = 3, max = 40, message = "2")
            String name) {

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
}
