package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage{
    protected final HashMap<Integer, User> userList = new HashMap<>();

    @Override
    public void createUser(User user) {
        userList.put(user.getId(), user);
    }

    @Override
    public void updateUser(User user) {
        userList.put(user.getId(), user);
    }

    @Override
    public void deleteUser(User user) {
        userList.remove(user.getId());
    }

    public HashMap<Integer, User> getUserList() {
        return userList;
    }
}
