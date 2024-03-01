package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
public class Film implements Comparable<Film>{
    protected int id;
    @NonNull @NotBlank
    protected String name;
    @NonNull @Size(min = 1, max = 200)
    protected String description;
    @NonNull
    protected LocalDate releaseDate;
    @NonNull @Min(1)
    protected int duration;
    protected int rate;
    protected int likeCount;
    protected Set<Long> likeUserId;

    @Override
    public int compareTo(Film o) {
        return this.likeCount - o.likeCount;
    }
}
