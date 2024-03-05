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
        ArrayList<Film> likedList = new ArrayList<>(filmStorage.getLikedFilmSet());
        System.out.println("Весь список - " + likedList);
        ArrayList<Film> returnedLikedList = new ArrayList<>();
        if (!likedList.isEmpty()) {
            if (count > likedList.size()) {
                for (Film film : likedList) {
                    returnedLikedList.add(0, film);
                }
            } else {
                for (int i = 0; i < count; i++) {
                    System.out.println(likedList.get(i));
                    returnedLikedList.add(0, likedList.get(i));
                }
            }
        }
        return returnedLikedList;
    }

    public InMemoryFilmStorage getStorage() {
        return filmStorage;
    }
}
