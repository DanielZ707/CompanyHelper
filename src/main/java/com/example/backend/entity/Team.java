package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTeam;
    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idConstruction", referencedColumnName = "idConstruction")
    private Construction construction;

    @OneToOne(mappedBy = "team")
    private User user;


    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public Long getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Long idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Construction getConstruction() {
        return construction;
    }

    public void setConstruction(Construction construction) {
        this.construction = construction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
