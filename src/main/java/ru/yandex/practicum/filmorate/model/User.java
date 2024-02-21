package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class User {
    protected int id;
    @NonNull @Email @NotBlank
    protected String email;
    @NonNull
    protected String login;
    @NonNull
    protected String name;
    @NonNull @PastOrPresent
    protected LocalDate birthday;
}
