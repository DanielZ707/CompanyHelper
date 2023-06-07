package com.example.backend.models.entity;

import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Expose
    private Long idTeam;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    @Expose
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idConstruction", referencedColumnName = "idConstruction")
    @Expose
    @Nullable
    private Construction construction;

    @OneToMany(mappedBy = "team")
    @ToString.Exclude
    @Nullable
    private transient Set<User> users = new HashSet<>();

    public Team(String name) {
        this.name = name;
    }
}
