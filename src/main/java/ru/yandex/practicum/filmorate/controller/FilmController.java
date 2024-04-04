package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Запрос на создание фильма {}", film);
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание слишком длинное");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.warn("Валидация даты фильма не пройдена");
            throw new ValidationException("Неверная дата релиза");
        }
        films.put(film.getId(), film);
        log.info("Фильм успешно создан");
        return films.get(film.getId());
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Запрос на обновление фильма {}", film);
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
            log.info("Фильм существует и обновляется");
            films.put(film.getId(), film);
        } else {
            log.info("Фильм не существует");
            throw new ValidationException("Film unknown");
        }
        return films.get(film.getId());
    }

    @GetMapping
    public List<Film> findAll() {
        List<Film> list = new ArrayList<>();
        for (Integer key : films.keySet()) {
            list.add(films.get(key));
        }
        return list;
    }

}
