package com.example.backend.models.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Email(message = "3")
    @Pattern(regexp = ".+@.+\\..+", message = "3")
    @NotBlank(message = "1")
    @NotNull(message = "0")
    @Size(min = 3, max = 64, message = "2")
    private String email;

    @NotNull(message = "0")
    @NotBlank(message = "1")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[\\]{}:;',?/*~$^+=<>]).{8,64}$", message = "4")
    String password;
}
