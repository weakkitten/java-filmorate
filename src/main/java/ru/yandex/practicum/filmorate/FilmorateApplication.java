package ru.yandex.practicum.filmorate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.LocalDateAdapter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;


@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);
		GsonBuilder gsonBuilder = new GsonBuilder();
		HttpClient client = HttpClient.newHttpClient();
		gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
		Gson gson = gsonBuilder.create();
		User user = new User("bii99@rambler.ru", "ilyabykov", "Имя",
				LocalDate.ofYearDay(1999, 207));
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/users"))
				.header("Content-type","application/json ")
				.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
				.build();
		HttpRequest requestLoad = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/users"))
				.GET()
				.build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			HttpResponse<String> responseSecond = client.send(requestLoad, HttpResponse.BodyHandlers.ofString());
			System.out.println(response);
			System.out.println(responseSecond.body());
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
