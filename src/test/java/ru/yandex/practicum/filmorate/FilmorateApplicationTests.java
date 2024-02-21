package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
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

@SpringBootTest
class FilmorateApplicationTests {

	GsonBuilder gsonBuilder = new GsonBuilder();
	HttpClient client = HttpClient.newHttpClient();

	@Test
	void contextLoads() {
		SpringApplication.run(FilmorateApplication.class);
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
		Gson gson = gsonBuilder.create();
		Film fil = new Film(0, "", "", LocalDate.now(), 3600);
		Film film = new Film(0, "Пользователь", "Описание", LocalDate.now(), 3600);
		String gsonString = gson.toJson(film);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/films"))
				.header("Content-type","application/json ")
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
