package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @PostMapping()
    public String createUser(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может содержать пробелы");
        } else {
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
/*            if (user.getId() == 0 && !userService.getUserStorage().getUserList().isEmpty()) {
                user.setId(userService.getUserStorage().getUserList().size() + 1);
            } else {
                user.setId(1);
            }*/
            userService.getUserStorage().createUser(user);
            log.debug("Пользователь успешно создан");
            return gson.toJson(user);
        }
    }

    @PutMapping("")
    public String updateUser(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Не может содержать пробелы");
        } else {
            User tempUser = userService.getUserStorage().getUserById(user.getId());
            if (user.getName() == null || user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            userService.getUserStorage().updateUser(user);
            log.debug("Обновление прошло успешно");
            return gson.toJson(user);
        }
    }

    @GetMapping()
    public String getAllUsers() {
        log.debug("Выгрузка пользователей");
        ArrayList<User> listUser = userService.getAllUsers();
        return gson.toJson(listUser);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable int id, @PathVariable int friendId) {
        int size = userService.getSize();
        if ((id > 0 && id <= size) && (friendId > 0 && friendId <= size)) {
            userService.addFriend(id, friendId);
            return gson.toJson(userService.getUserStorage().getUserById(id));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteUser(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
        return gson.toJson(userService.getUserStorage().getUserById(id));
    }

    @GetMapping("/{id}/friends")
    public String returnFriend(@PathVariable int id) {
        ArrayList<User> friendList = new ArrayList<>();
        for (int idFriend : userService.getUserStorage().getUserById(id).getFriends().keySet()) {
            friendList.add(userService.getUserStorage().getUserById(idFriend));
        }
        return gson.toJson(friendList);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public String getMutualFriend(@PathVariable int id, @PathVariable int otherId) {
        return gson.toJson(userService.getMutualFriend(id, otherId));
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable int id) {
        if (userService.getUserStorage().getUserById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return gson.toJson(userService.getUserStorage().getUserById(id));
        }
    }
}
