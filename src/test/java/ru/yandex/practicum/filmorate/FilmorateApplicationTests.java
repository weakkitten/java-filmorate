package ru.yandex.practicum.filmorate;

import com.google.gson.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
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
		Film film = new Film("Пользователь", "Описание", LocalDate.now(), 3600);
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
			JsonElement jsonElement = JsonParser.parseString(responseSecond.body());
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			film.setId(1);
			Assertions.assertEquals(film, gson.fromJson(jsonArray.get(0), Film.class));
		} catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
