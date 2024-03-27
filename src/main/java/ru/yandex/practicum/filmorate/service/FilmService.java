package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmDbStorage;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FilmService {
    protected final FilmDbStorage filmStorage;

    public void addLike(int userId, int filmId) {
        Film film = filmStorage.getFilmById(filmId);
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
        Film film = filmStorage.getFilmById(filmId);
        if (!film.getLikeUserId().contains(userId)) {
            int like = film.getLikeCount() - 1;
            film.setLikeCount(like);
            film.getLikeUserId().remove(userId);
            filmStorage.updateFilm(film);
        }
    }

    public ArrayList<Film> getTopLikedFilms(int count) {
        return filmStorage.getTopLikedFilms(count);
    }


    public FilmDbStorage getStorage() {
        return filmStorage;
    }
}
