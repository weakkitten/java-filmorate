package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashMap;

@Data
@Builder
public class User {
    protected int id;
    @NonNull @Email @NotBlank protected String email;
    @NonNull @NotBlank protected String login;
    protected String name;
    @NonNull @Past protected LocalDate birthday;
    protected HashMap<Integer, FriendStatus> friends = new HashMap();

    public void addFriend(int id) {
        if (!friends.containsKey(id)) {
            friends.put(id, FriendStatus.WAITING);
        }
    }

    public void removeFriend(int id) {
        friends.remove(id);
    }

    public void confirmFriend(int id) {
        friends.put(id, FriendStatus.CONFIRMED);
    }
}
