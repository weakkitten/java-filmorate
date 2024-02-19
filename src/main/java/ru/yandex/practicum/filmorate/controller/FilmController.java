package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.PastOrPresent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> filmMap = new HashMap<>();
    private final Gson gson = new Gson();

    @PostMapping()
    public String addFilm(@Valid @PastOrPresent @RequestBody Film film) {
        LocalDate firstFilm = LocalDate.ofYearDay(1985,362);
        if (film.getDescription().length() <= 200 && film.getDuration() > 0
                && film.getReleaseDate().isAfter(firstFilm) && !film.getName().isEmpty()) {
            filmMap.put(film.getId(), film);
            log.debug("Фильм успешно добавлен");
        } else {
            throw new ValidationException();
        }
        return film.toString();
    }

    @PostMapping("/film")
    public void refreshFilm(@RequestParam int value, @Valid @PastOrPresent @RequestBody Film film) {
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
        log.debug("Выгрузка прошла");
        return gson.toJson(filmMap);
    }
}
