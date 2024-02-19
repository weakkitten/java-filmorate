package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    @NonNull protected int id;
    @NonNull protected String name;
    @NonNull protected String description;
    @NonNull protected LocalDate releaseDate;
    @NonNull protected int duration;
}
