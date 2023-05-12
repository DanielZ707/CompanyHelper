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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Service
public class ConstructionController {

    private final ConstructionRepo constructionRepo;
    java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

    @Autowired
    public ConstructionController(ConstructionRepo constructionRepo) {

        this.constructionRepo = constructionRepo;
    }

    @PostMapping("/addConstruction")
    public ResponseEntity<String> addConstruction(@Valid @RequestBody ConstructionController.ConstructionRequest request) {


        if (constructionRepo.findByName(request.name()) != null) {
            return new ResponseEntity<>("This construction has already existed", HttpStatus.BAD_REQUEST);
        }
        else{
            Construction construction = constructionRepo.save(new Construction(request.name(), request.town(), request.street(),request.buildingNumber(),request.dateOfBegging(), request.deadlineDay()));
            String constructionJsonString = new Gson().toJson(construction);
            return new ResponseEntity<>(constructionJsonString, HttpStatus.OK);
        }
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
            Date deadlineDay){

    }
}
