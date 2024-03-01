package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FilmService {
    protected final InMemoryFilmStorage filmStorage;

    public void addLike(long userId, int filmId) {
        if (!filmStorage.getFilmMap().get(filmId).getLikeUserId().contains(userId)) {
            int like = filmStorage.getFilmMap().get(filmId).getLikeCount() + 1;
            filmStorage.getFilmMap().get(filmId).setLikeCount(like);
            filmStorage.getFilmMap().get(filmId).getLikeUserId().add(userId);
        }
    }

    public void removeLike(long userId, int filmId) {
        if (!filmStorage.getFilmMap().get(filmId).getLikeUserId().contains(userId)) {
            int like = filmStorage.getFilmMap().get(filmId).getLikeCount() - 1;
            filmStorage.getFilmMap().get(filmId).setLikeCount(like);
            filmStorage.getFilmMap().get(filmId).getLikeUserId().remove(userId);
        }
    }

    public ArrayList<Film> getTopLikedFilms(int count) {
        ArrayList<Film> likedList = new ArrayList<>(filmStorage.getLikedFilmSet());
        ArrayList<Film> returnedLikedList = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            returnedLikedList.add(0, likedList.get(i));
        }
        return returnedLikedList;
    }

    public InMemoryFilmStorage getStorage() {
        return filmStorage;
    }
}
