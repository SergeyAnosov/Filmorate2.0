package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Запрос на создание юзера {}", user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Юзер успешно создан");
        return users.get(user.getId());
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Запрос на обновление юзера {}", user);
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
            log.info("Юзер существует и будет обновлен");
            users.put(user.getId(), user);
        } else {
            log.info("Юзер не существует");
            throw new ValidationException("User unknown");
        }
        return users.get(user.getId());
    }

    @GetMapping
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        for (Integer key : users.keySet()) {
            list.add(users.get(key));
        }
        return list;
    }

}
