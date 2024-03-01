package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer,User> userList = new HashMap<>();
    private final GsonBuilder gsonBuilder = new GsonBuilder();

    @PostMapping()
    public void createUser(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        } else {
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            if (user.getId() == 0 && !userList.isEmpty()) {
                user.setId(userList.size() + 1);
            } else {
                user.setId(1);
            }
            userList.put(userList.size(), user);
            log.debug("Пользователь успешно создан");
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            Gson gson = gsonBuilder.create();
            return gson.toJson(user);
        }
    }

    @PutMapping("")
    public void updateUser(@Valid @RequestBody User user) {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();

        if (user.getLogin().contains(" ") || user.getId() > userList.size()) {
            throw new ValidationException("Не может содержать пробелы");
        } else {
            User tempUser = userList.get(user.getId());
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            userList.put(user.getId() - 1, user);
            log.debug("Обновление прошло успешно");
            return gson.toJson(user);
        }
    }

    @GetMapping()
    public String getAllUsers() {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        log.debug("Выгрузка пользователей");
        ArrayList<User> listUser = new ArrayList<>(userList.values());
        return gson.toJson(listUser);
    }


    @DeleteMapping
    public void deleteUser() {
        return null;
    }
}
