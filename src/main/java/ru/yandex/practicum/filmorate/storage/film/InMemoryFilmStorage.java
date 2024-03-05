package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.TreeSet;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    protected final HashMap<Integer, Film> filmMap = new HashMap<>();
    protected final TreeSet<Film> likedFilmSet = new TreeSet<>();

    @Override
    public void addFilm(Film film) {
        filmMap.put(film.getId(), film);
        likedFilmSet.add(film);
        System.out.println("В создании - " + likedFilmSet);
    }

    @Override
    public void updateFilm(Film film) {
        filmMap.put(film.getId(), film);
        likedFilmSet.remove(filmMap.get(film.getId()));
        likedFilmSet.add(film);
    }

    @Override
    public void deleteFilm(Film film) {
        filmMap.remove(film.getId());
        likedFilmSet.remove(film);
    }

    public HashMap<Integer, Film> getFilmMap() {
        return filmMap;
    }

    public TreeSet<Film> getLikedFilmSet() {
        return likedFilmSet;
    }
}
