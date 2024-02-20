package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class User {
    @NonNull
    protected int id;
    @NonNull @Email @NotBlank
    protected String email;
    @NonNull
    protected String login;
    @NonNull
    protected String name;
    @NonNull
    protected LocalDate birthday;
}
