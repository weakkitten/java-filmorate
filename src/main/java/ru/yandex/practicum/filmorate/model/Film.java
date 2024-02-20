package ru.yandex.practicum.filmorate.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    @NonNull
    protected int id;
    @NonNull
    protected String name;
    @NonNull
    @Size(min = 1, max = 200)
    protected String description;
    @NonNull
    protected LocalDate releaseDate;
    @NonNull
    @Min(1)
    protected int duration;
}
