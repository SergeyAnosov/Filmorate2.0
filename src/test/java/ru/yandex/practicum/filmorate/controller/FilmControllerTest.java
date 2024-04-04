package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    @Test
    void create() {
        FilmController filmController = new FilmController();
        LocalDate release = LocalDate.of(1712, 10, 10);
        Film film = new Film(1, "name", "description", release, 100);

        assertThrows(ValidationException.class, () -> filmController.create(film));

    }

    @Test
    void update() {
    }
}