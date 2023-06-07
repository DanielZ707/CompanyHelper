package com.example.backend.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "constructions")
public class Construction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long idConstruction;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    @Expose
    private String name;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    @Expose
    private String town;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Size(min = 3, max = 30, message = "2")
    @Expose
    private String street;

    @NotNull(message = "0")
    @Min(1)
    @Expose
    private int buildingNumber;

    @NotNull(message = "0")
    @Expose
    private int progress;

    @JsonFormat(pattern=("yyyy-MM-dd"))
    @Temporal(TemporalType.DATE)
    @Expose
    private Date dateOfBegging;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern=("yyyy-MM-dd"))
    @Expose
    private Date deadlineDay;

    @OneToOne(mappedBy = "construction")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Nullable
    private Team team;

    public Construction(String name, String town, String street, int buildingNumber, int progress, Date dateOfBegging, Date deadlineDay) {
        this.name = name;
        this.town = town;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.progress = progress;
        this.dateOfBegging = dateOfBegging;
        this.deadlineDay = deadlineDay;
    }
}
