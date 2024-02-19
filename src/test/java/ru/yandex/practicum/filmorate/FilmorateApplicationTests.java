package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	HttpClient client = HttpClient.newHttpClient();

	@Test//Оно не работает..
	void contextLoads() {
		Film film = new Film(0, "Пользователь", "Описание", LocalDate.now(), 3600);
		User user = new User(0,"bii99@rambler.ru", "ilyabykov", "Имя",
				LocalDate.ofYearDay(1999, 207));
		System.out.println(gson.toJson(film));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/films"))
				.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(film)))
				.build();
		HttpRequest requestLoad = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/films/film?id=0"))
				.GET()
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			HttpResponse<String> responseSecond = client.send(requestLoad, HttpResponse.BodyHandlers.ofString());
			Assertions.assertEquals(gson.toJson(film), responseSecond.body());
		} catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
