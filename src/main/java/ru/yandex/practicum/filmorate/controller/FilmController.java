package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class FilmController {

    @PostMapping("/films")
    public void addFilm(@RequestBody Film film) {

    }

    @PostMapping("/films")
    public void refreshFilm(@RequestBody Film film) {

    }

    @GetMapping("/films")
    public ArrayList<Film> getAllFilm() {
        return null;
    }
}
