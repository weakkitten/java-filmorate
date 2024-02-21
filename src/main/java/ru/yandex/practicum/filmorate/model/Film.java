package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    protected int id;
    @NonNull @NotBlank
    protected String name;
    @NonNull @Size(min = 1, max = 200)
    protected String description;
    @NonNull
    protected LocalDate releaseDate;
    @NonNull @Min(1)
    protected int duration;
    protected int rate;
}
