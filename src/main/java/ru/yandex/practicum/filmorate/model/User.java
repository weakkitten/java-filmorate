package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    @NonNull protected int id;
    @NonNull @Email @NotBlank protected String email;
    @NonNull protected String login;
    @NonNull protected String name;
    @NonNull protected LocalDate birthday;
}
