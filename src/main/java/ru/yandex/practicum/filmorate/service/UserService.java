package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dao.UserDbStorage;

import java.util.ArrayList;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {
    protected final UserDbStorage userStorage;

    public void addFriend(Integer idFriend1, Integer idFriend2) {
        User userFirst = userStorage.getUserById(idFriend1);
        userFirst.addFriend(idFriend2);
        userStorage.updateUser(userFirst);
    }

    public void confirmedFriend(Integer idFriend1, Integer idFriend2) {
        User userFirst = userStorage.getUserById(idFriend1);
        userFirst.confirmFriend(idFriend2);
        userStorage.updateUser(userFirst);
    }

    public void removeFriend(int idFriend1, int idFriend2) {
        User userFirst = userStorage.getUserById(idFriend1);
        userFirst.removeFriend(idFriend2);
        userStorage.updateUser(userFirst);
    }

    public ArrayList<User> getMutualFriend(int idFriend1, int idFriend2) {
        User userFirst = userStorage.getUserById(idFriend1);
        User userSecond = userStorage.getUserById(idFriend2);
        ArrayList<User> mutualFriends = new ArrayList<>();
        if (userSecond.getFriends() != null || userFirst.getFriends() != null) {
            for (int idFirst : userFirst.getFriends().keySet()) {
                if (userSecond.getFriends().containsKey(idFirst)) {
                    mutualFriends.add(userStorage.getUserById(idFirst));
                }
            }
        }
        return mutualFriends;
    }

    public ArrayList<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public Integer getSize() {
        return userStorage.getMaxId();
    }
}
