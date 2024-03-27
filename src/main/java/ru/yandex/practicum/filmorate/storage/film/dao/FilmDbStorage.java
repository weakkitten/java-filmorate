package ru.yandex.practicum.filmorate.storage.film.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class FilmDbStorage implements FilmStorage {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilm(Film film) {
        String sqlQuery = "insert into films(name, description, releasedate, duration, rate, likeCount, likeUserId," +
                "ageRating, genre) " + "values (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery,film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), film.getLikeCount(), film.getLikeUserId().toString(), film.getAgeRating(),
                film.getGenre());
    }

    @Override
    public void updateFilm(Film film) {
        String sqlQuery = "update  films set name = ?, description = ?, releasedate = ?, duration = ?, rate = ?, " +
                "likeCount = ?, likeUserId = ?, ageRating = ? ,genre = ?";
        jdbcTemplate.update(sqlQuery,film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getRate(), film.getLikeCount(), film.getLikeUserId().toString(), film.getAgeRating(),
                film.getGenre());
    }

    @Override
    public void deleteFilm(Film film) {
        String sqlQuery = "delete from films where id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    public Film getFilmById(int id) {
        String sqlQuery = "select * from films where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        String[] likeId = resultSet.getString("likeUserId").split(",");
        Set<Integer> likeSet = new HashSet<>();
        for (String like : likeId) {
            likeSet.add(Integer.parseInt(like));
        }
        return Film.builder()
                .id(resultSet.getInt("id"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("releasedate").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .rate(resultSet.getInt("rate"))
                .likeCount(resultSet.getInt("likeCount"))
                .likeUserId(likeSet)
                .ageRating(MpaRating.valueOf(resultSet.getString("ageRating")))
                .genre(Genre.valueOf(resultSet.getString("genre")))
        .build();
    }
}
