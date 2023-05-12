package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "constructions")
public class Construction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConstruction;
    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    private String name;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    private String town;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    private String street;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 1, message = "2")
    private int buildingNumber;

    private Date dateOfBegging;
    private Date deadlineDay;

    @OneToOne(mappedBy = "construction")
    private Team team;

    public Construction() {
    }

    public Construction(String name, String town, String street, int buildingNumber, Date dateOfBegging, Date deadlineDay) {
        this.name = name;
        this.town = town;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.dateOfBegging = dateOfBegging;
        this.deadlineDay = deadlineDay;
    }

    public Long getIdConstruction() {
        return idConstruction;
    }

    public void setIdConstruction(Long idConstruction) {
        this.idConstruction = idConstruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public Date getDateOfBegging() {
        return dateOfBegging;
    }

    public void setDateOfBegging(Date dateOfBegging) {
        this.dateOfBegging = dateOfBegging;
    }

    public Date getDeadlineDay() {
        return deadlineDay;
    }

    public void setDeadlineDay(Date deadlineDay) {
        this.deadlineDay = deadlineDay;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
