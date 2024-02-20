package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class FilmorateApplicationTests {

	GsonBuilder gsonBuilder = new GsonBuilder();
	HttpClient client = HttpClient.newHttpClient();

	@Test//Оно не работает
	void contextLoads() {
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
		Gson gson = gsonBuilder.create();
		Film film = new Film(0, "Пользователь", "Описание", LocalDate.now(), 3600);
		User user = new User(0,"bii99@rambler.ru", "ilyabykov", "Имя",
				LocalDate.ofYearDay(1999, 207));
		System.out.println(film);
		String gsonString = gson.toJson(film);
		System.out.println(gson.toJson(gsonString));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/films"))
				.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(film)))
				.build();
		HttpRequest requestLoad = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/films"))
				.GET()
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			HttpResponse<String> responseSecond = client.send(requestLoad, HttpResponse.BodyHandlers.ofString());
			JsonElement jsonElement = JsonParser.parseString(responseSecond.body().substring(5,responseSecond.body().length() - 1));
			Assertions.assertEquals(film, gson.fromJson(jsonElement, Film.class));
		} catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
