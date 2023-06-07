package com.example.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long idUser;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 12, message = "2")
    @Expose
    private String name;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 12, message = "2")
    @Expose
    private String surname;

    @Email(message = "4")
    @Pattern(regexp = ".+@.+\\..+", message = "3")
    @NotBlank(message = "1")
    @NotNull(message = "0")
    @Size(min = 3, max = 50, message = "2")
    @Expose
    private String email;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message =
            "2")
    @Expose
    private String password;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Pattern(regexp = "^[0-9]{6,12}$", message = "2")
    @Expose
    private String telephone;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 15, message = "2")
    @Expose
    private String job;

    @Expose
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTeam", referencedColumnName = "idTeam")
    @Expose
    private Team team;

    @JsonFormat(pattern = ("yyyy-MM-dd"))
    @Temporal(TemporalType.DATE)
    @Expose
    private Date creationDate;
    @JsonFormat(pattern = ("yyyy-MM-dd"))
    @Temporal(TemporalType.DATE)
    @Expose
    private Date lastLoginDate;

    public User(String name, String surname, String email, String password, String telephone, String job, Role role, Team team) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.job = job;
        this.creationDate = new Date();
        this.lastLoginDate = new Date();
        this.role = role;
        this.team = team;
    }

    public enum Role {
        ADMIN,
        WORKER,
        MANAGER
    }
}
