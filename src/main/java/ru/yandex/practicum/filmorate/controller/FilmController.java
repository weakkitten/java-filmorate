package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> filmMap = new HashMap<>();

    private final GsonBuilder gsonBuilder = new GsonBuilder();

    @PostMapping()
    public String addFilm(@Valid @RequestBody Film film) {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        LocalDate firstFilm = LocalDate.ofYearDay(1985,362);
        if (film.getId() == 0 && !filmMap.isEmpty()) {
            film.setId(filmMap.size());
        }
        if (film.getReleaseDate().isAfter(firstFilm)) {
            filmMap.put(film.getId(), film);
            log.debug("Фильм успешно добавлен");
        } else {
            throw new ValidationException("Фильмы не совпадают");
        }

        return film.toString();
    }

    @PostMapping("/film")
    public void refreshFilm(@RequestParam int value,@Valid @RequestBody Film film) {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        Film tempFilm = filmMap.get(value);
        if (tempFilm.equals(film)) {
            filmMap.put(film.getId(), film);
            log.debug("Замена успешно произведена");
        } else {
            log.debug("Фильмы разные");
            throw new ValidationException("Фильмы разные");
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
