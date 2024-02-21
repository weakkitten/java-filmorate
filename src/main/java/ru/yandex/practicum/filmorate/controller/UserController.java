package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
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
        if (user.getName().contains(" ") || user.getLogin().contains(" ")) {
            throw new ValidationException("Не может содержать пробелы");
        } else {
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            Gson gson = gsonBuilder.create();
            if (user.getId() == 0 && !userList.isEmpty()) {
                user.setId(userList.size());
            }
            if (user.getName().contains(" ") || user.getLogin().contains(" ")) {
                throw new ValidationException("Не может содержать пробелы");
            } else {
                try {
                    userList.put(user.getId(), user);
                } catch (ValidationException e) {
                    e.getMessage();
                }
            }
        }
    }

    @PostMapping("/user")
    public void updateUser(@RequestParam int value,@Valid @RequestBody User user) {
        if (user.getName().contains(" ") || user.getLogin().contains(" ")) {
            throw new ValidationException("Не может содержать пробелы");
        } else {
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            Gson gson = gsonBuilder.create();
            User tempUser = userList.get(value);
            if (user.equals(tempUser)) {
                userList.put(user.getId(), user);
                log.debug("Обновление прошло успешно");
            } else {
                log.debug("Пользователь не совпадает");
                throw new ValidationException("Пользователь не совпадает");
            }
        }
    }

    @GetMapping()
    public String getAllUsers() {
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        Gson gson = gsonBuilder.create();
        log.debug("Выгрузка пользователей");
        return gson.toJson(userList);
    }
}
