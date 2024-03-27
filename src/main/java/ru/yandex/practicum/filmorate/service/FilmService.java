package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmDbStorage;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {
    protected final FilmDbStorage filmStorage;

    public void addLike(int userId, int filmId) {
        Film film = filmStorage.getFilmMap().get(filmId);
        if (film.getLikeUserId() == null) {
            int like = film.getLikeCount() + 1;
            film.setLikeCount(like);
            film.getLikeUserId().add(userId);
            filmStorage.updateFilm(film);
        } else {
            if (!film.getLikeUserId().contains(userId)) {
                int like = film.getLikeCount() + 1;
                film.setLikeCount(like);
                film.getLikeUserId().add(userId);
                filmStorage.updateFilm(film);
            }
        }
    }

    public void removeLike(int userId, int filmId) {
        if (!filmStorage.getFilmMap().get(filmId).getLikeUserId().contains(userId)) {
            int like = filmStorage.getFilmMap().get(filmId).getLikeCount() - 1;
            filmStorage.getFilmMap().get(filmId).setLikeCount(like);
            filmStorage.getFilmMap().get(filmId).getLikeUserId().remove(userId);
        }
    }

    public ArrayList<Film> getTopLikedFilms(int count) {
        return (ArrayList<Film>) filmStorage.getFilm().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikeCount(), f1.getLikeCount()))
                .limit(count)
                .collect(Collectors.toList());
    }


    public InMemoryFilmStorage getStorage() {
        return filmStorage;
    }
}
