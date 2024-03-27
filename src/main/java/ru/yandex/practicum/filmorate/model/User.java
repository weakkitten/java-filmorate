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

    public void addFriend(int id, FriendStatus status) {
        if (!friends.containsKey(id)) {
            friends.put(id, status);
        } else {
            FriendStatus friendStatus = friends.get(id);
            if (friendStatus != status) {
                friends.put(id, status);
            }
        }
    }

    public void removeFriend(int id) {
        friends.remove(id);
    }
}
