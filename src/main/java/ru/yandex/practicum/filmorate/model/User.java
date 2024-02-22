package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    protected int id;
    @NonNull @Email @NotBlank
    protected String email;
    @NonNull @NotBlank
    protected String login;
    protected String name;
    @NonNull @Past
    protected LocalDate birthday;
}
