package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;

public interface UserStorage {
    public void createUser(@Valid @RequestBody User user);

    public void updateUser(@Valid @RequestBody User user);

    public void deleteUser(@Valid @RequestBody User user);
}
