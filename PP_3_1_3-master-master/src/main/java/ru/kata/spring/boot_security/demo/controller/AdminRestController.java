package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;


    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    // Получить всех пользователей (GET /api/admin/)
    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users); // 200 OK
    }

    // Создать нового пользователя (POST /api/admin/new)
    @PostMapping()
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user); // 201 Created
    }

    // Удалить пользователя по ID (DELETE /api/admin/delete/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Получить пользователя по ID для обновления (GET /api/admin/update?id=...)
    @GetMapping("/{id}")
    public User updateForm(@PathVariable("id") Long id) {
        return userService.getById(id);

    }

    // Обновить пользователя (PUT /api/admin/update)
    @PutMapping("/edit")
    public ResponseEntity<User> update(@RequestBody User user) {
        User existingUser = userService.getById(user.getId());
        if (existingUser != null) {
            userService.update(user);
            return ResponseEntity.ok(user); // 200 OK
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }
    }
}