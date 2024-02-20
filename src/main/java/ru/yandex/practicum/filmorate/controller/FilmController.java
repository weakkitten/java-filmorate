package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> filmMap = new HashMap<>();

    private final GsonBuilder gsonBuilder = new GsonBuilder();

    @PostMapping()
    public String addFilm(@RequestBody Film film) {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        LocalDate firstFilm = LocalDate.ofYearDay(1985,362);
        if (film.getReleaseDate().isAfter(firstFilm)) {
            filmMap.put(film.getId(), film);
            log.debug("Фильм успешно добавлен");
        } else {
            throw new ValidationException();
        }
        return film.toString();
    }

    @PostMapping("/film")
    public void refreshFilm(@RequestParam int value, @RequestBody Film film) {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        Film tempFilm = filmMap.get(value);
        if (tempFilm.equals(film)) {
            filmMap.put(film.getId(), film);
            log.debug("Замена успешно произведена");
        } else {
            log.debug("Фильмы разные");
            throw new ValidationException();
        }
    }

    @GetMapping()
    public String getAllFilm() {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        log.debug("Выгрузка прошла");
        return gson.toJson(filmMap);
    }
}
