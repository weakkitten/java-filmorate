package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import ru.yandex.practicum.filmorate.model.User;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer,User> userList = new HashMap<>();
    private final Gson gson = new Gson();
    @PostMapping()
    public void createUser(@Valid @Email @NotBlank @Past User user) {
        try {
            userList.put(user.getId(), user);
        } catch (ValidationException e) {
            e.getMessage();
        }
    }

    @PostMapping("/user")
    public void updateUser(@RequestParam int value, @Valid @Email @NotBlank @Past User user) {
        User tempUser = userList.get(value);
        if (user.equals(tempUser)) {
            userList.put(user.getId(), user);
            log.debug("Обновление прошло успешно");
        } else {
            log.debug("Пользователь не совпадает");
            throw new ValidationException();
        }
    }

    @GetMapping()
    public String getAllUsers() {
        log.debug("Выгрузка пользователей");
        return gson.toJson(userList);
    }
}
