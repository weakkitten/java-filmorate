package ru.yandex.practicum.filmorate.storage.film;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    protected final HashMap<Integer, Film> filmMap = new HashMap<>();

    @Override
    public void addFilm(Film film) {
        filmMap.put(film.getId(), film);
    }

    @Override
    public void updateFilm(Film film) {
        filmMap.put(film.getId(), film);
    }

    @Override
    public void deleteFilm(Film film) {
        filmMap.remove(film.getId());
    }

    public ArrayList<Film> getFilm() {
        return new ArrayList<Film>(filmMap.values());
    }
}
