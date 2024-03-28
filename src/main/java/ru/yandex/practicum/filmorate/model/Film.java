package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film implements Comparable<Film> {
    protected int id;
    @NonNull @NotBlank protected String name;
    @NonNull @Size(min = 1, max = 200) protected String description;
    @NonNull protected LocalDate releaseDate;
    @NonNull @Min(1) protected int duration;
    protected Genre genre;
    protected MpaRating ageRating;
    protected int rate;
    protected int likeCount;
    protected Set<Integer> likeUserId = new HashSet<>();

    @Override
    public int compareTo(Film o) {
        return o.likeCount - this.likeCount;
    }
}
