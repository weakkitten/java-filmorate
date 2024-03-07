package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;

public interface FilmStorage {

    public void addFilm(@Valid @RequestBody Film film);

    public void updateFilm(@Valid @RequestBody Film film);

    public void deleteFilm(@Valid @RequestBody Film film);
}
