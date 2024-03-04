package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    protected final InMemoryUserStorage userStorage;

    public void addFriend(int idFriend1, int idFriend2) {
        User userFirst = userStorage.getUserList().get(idFriend1);
        User userSecond = userStorage.getUserList().get(idFriend2);
        userFirst.getFriends().add(idFriend2);
        userSecond.getFriends().add(idFriend1);
        userStorage.updateUser(userFirst);
        userStorage.updateUser(userSecond);
    }

    public void removeFriend(int idFriend1, int idFriend2) {
        User userFirst = userStorage.getUserList().get(idFriend1);
        User userSecond = userStorage.getUserList().get(idFriend2);
        userFirst.getFriends().remove(idFriend2);
        userSecond.getFriends().remove(idFriend1);
        userStorage.updateUser(userFirst);
        userStorage.updateUser(userSecond);
    }

    public ArrayList<User> getMutualFriend(int idFriend1, int idFriend2) {
        User userFirst = userStorage.getUserList().get(idFriend1);
        User userSecond = userStorage.getUserList().get(idFriend2);
        ArrayList<User> mutualFriends = new ArrayList<>();
        if (userSecond.getFriends() != null || userFirst.getFriends() != null) {
            for (int idFirst : userFirst.getFriends()) {
                if (userSecond.getFriends().contains(idFirst)) {
                    mutualFriends.add(userStorage.getUserList().get(idFirst));
                }
            }
        }
        return mutualFriends;
    }

    public InMemoryUserStorage getUserStorage() {
        return userStorage;
    }
}
