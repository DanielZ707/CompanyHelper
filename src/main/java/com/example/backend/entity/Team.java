package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "team")
    @ToString.Exclude
    private transient Set<User> users = new HashSet<>();


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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
