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
import java.util.ArrayList;
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

        LocalDate firstFilm = LocalDate.ofYearDay(1895,362);
        if (film.getId() == 0 && !filmMap.isEmpty()) {
            film.setId(filmMap.size());
        } else {
            film.setId(1);
        }
        if (film.getReleaseDate().isAfter(firstFilm)) {
            filmMap.put(film.getId(), film);
            log.debug("Фильм успешно добавлен");
            return gson.toJson(film);
        } else {
            throw new ValidationException("Раньше дня рождения кино");
        }
    }

    @PutMapping()
    public String refreshFilm(@Valid @RequestBody Film film) {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        LocalDate firstFilm = LocalDate.ofYearDay(1885,362);

        if (film.getId() > filmMap.size()){
            throw new ValidationException("Слишком большой id");
        }

        if (film.getReleaseDate().isAfter(firstFilm)) {
            filmMap.put(film.getId(), film);
            log.debug("Замена успешно произведена");
            return gson.toJson(film);
        } else {
            throw new ValidationException("Раньше дня рождения кино");
        }
    }

    @GetMapping()
    public String getAllFilm() {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        log.debug("Выгрузка прошла");
        ArrayList<Film> filmList = new ArrayList<>(filmMap.values());
        return gson.toJson(filmList);
    }
}
