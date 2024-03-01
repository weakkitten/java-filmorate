package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Service
@RequiredArgsConstructor
public class UserService {
    protected final InMemoryUserStorage userStorage;

    public void addFriend(int idFriend1, int idFriend2) {
        userStorage.getUserList().
    }

    public void removeFriend(int idFriend1, int idFriend2) {

    }

    public void getMutualFriend(int idFriend1, int idFriend2) {

    }
}
