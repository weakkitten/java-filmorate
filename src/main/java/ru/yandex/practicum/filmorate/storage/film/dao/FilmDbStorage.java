package ru.yandex.practicum.filmorate.storage.film.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component

public class FilmDbStorage implements FilmStorage {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private DatabaseMetaData Util;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilm(Film film) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FILMS")
                .usingGeneratedKeyColumns("id");
        int id = insert.executeAndReturnKey(filmToMap(film)).intValue();
        film.setId(id);
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

    public ArrayList<Film> getTopLikedFilms(int limit) {
        ArrayList<Film> films = (ArrayList<Film>) this.jdbcTemplate.query(
                "select first_name, last_name from t_actor",
                (resultSet, rowNum) -> {
                    String[] likeId = resultSet.getString("likeUserId").split(",");
                    Set<Integer> likeSet = new HashSet<>();
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
                });
        return films;
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

    private Map<String, Object> filmToMap(Film film) {
        return Map.of(
                "id",film.getId(),
                "name", film.getName(),
                "description", film.getReleaseDate(),
                "duration", film.getDuration()
        );
    }

    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM films";
        try (PreparedStatement ps = Util.getConnection().prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()
        ) {
            while (resultSet.next()) {
                String[] likeId = resultSet.getString("likeUserId").split(",");
                Set<Integer> likeSet = new HashSet<>();
                Film film = Film.builder()
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
                films.add(film);
            }
        } catch (SQLException ignored) {
        }
        return films;
    }

    public Integer getMaxId() {
        String sqlQuery = "select MAX(id) from films";
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
    }
}
