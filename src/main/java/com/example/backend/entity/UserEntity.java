package com.example.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 12, message = "2")
    private String name;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 12, message = "2")
    private String surname;
    @Email(message = "3")
    @Pattern(regexp = ".+@.+\\..+", message = "3")
    @NotBlank(message = "1")
    @NotNull(message = "0")
    @Size(min = 3, max = 50, message = "2")
    private String email;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
            "4")
    private String password;
    private Role role;
    private Team team;
    private Date creationDate;
    private Date lastLoginDate;

    public UserEntity() {
    }

    public UserEntity(String name, String surname, String email, String password, Role role, Team team) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.creationDate = new Date();
        this.lastLoginDate = new Date();
        this.role = role;
        this.team = team;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public enum Role {
        WORKER,
        MANAGER
    }

    public enum Team {
        MANAGER,
        ALFA,
        BETA,
        GAMMA,
        DELTA
    }
}
