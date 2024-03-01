package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    @Autowired
    private final FilmService filmService;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();


    @PostMapping()
    public String addFilm(@Valid @RequestBody Film film) {
        LocalDate firstFilm = LocalDate.ofYearDay(1895,362);
        if (film.getId() == 0 && !filmService.getStorage().getFilmMap().isEmpty()) {
            film.setId(filmService.getStorage().getFilmMap().size());
        } else {
            film.setId(1);
        }
        if (film.getReleaseDate().isAfter(firstFilm)) {
            filmService.getStorage().addFilm(film);
            log.debug("Фильм успешно добавлен");
            return gson.toJson(film);
        } else {
            throw new ValidationException("Раньше дня рождения кино");
        }
    }

    @PutMapping()
    public String refreshFilm(@Valid @RequestBody Film film) {
        LocalDate firstFilm = LocalDate.ofYearDay(1885,362);

        if (film.getId() > filmService.getStorage().getFilmMap().size()) {
            throw new ValidationException("Слишком большой id");
        }

        if (film.getReleaseDate().isAfter(firstFilm)) {
            filmService.getStorage().updateFilm(film);
            log.debug("Замена успешно произведена");
            return gson.toJson(film);
        } else {
            throw new ValidationException("Раньше дня рождения кино");
        }
    }

    @GetMapping()
    public String getAllFilm() {
        log.debug("Выгрузка прошла");
        ArrayList<Film> filmList = new ArrayList<>(filmService.getStorage().getFilmMap().values());
        return gson.toJson(filmList);
    }
}
