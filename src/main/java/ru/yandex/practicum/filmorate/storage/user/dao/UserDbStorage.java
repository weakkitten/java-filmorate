package ru.yandex.practicum.filmorate.storage.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UserDbStorage implements UserStorage {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        String sqlQuery = "insert into users(email, login, name, BIRTHDAY, friends) values (?,?,?,?,?)";
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(),
                user.getFriends().keySet().toString());
    }

    @Override
    public void updateUser(User user) {
        String sqlQuery = "update users email = ?, login = ?, name = ?, BIRTHDAY = ?, friends = ? where id = ?";
        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(),
                user.getFriends().keySet().toString());

    }

    @Override
    public void deleteUser(User user) {
        String sqlQuery = "delete from users where id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    public User getUserById(int id) {
        String sqlQuery = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        String[] friendsId = resultSet.getString("likeUserId").split(",");
        HashMap<Integer, FriendStatus> friendsSet = new HashMap<>();
        for (String like : friendsId) {
            friendsSet.put(Integer.parseInt(like), FriendStatus.CONFIRMED);
        }
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("BIRTHDAY").toLocalDate())
                .friends(friendsSet)
                .build();
    }
}
