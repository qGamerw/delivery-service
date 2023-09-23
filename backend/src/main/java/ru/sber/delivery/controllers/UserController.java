package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.services.ShiftService;
import ru.sber.delivery.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов о пользователях (курьерах)
 */
@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    private UserService userService;
    private ShiftService shiftService;

    /**
     * Конструктор контроллера пользователей
     */
    @Autowired
    public UserController(UserService userService, ShiftService shiftService) {
        this.userService = userService;
        this.shiftService = shiftService;
    }

    /**
     * Обновляет информацию о пользоватлене
     */
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        log.info("Обновление данные пользователя");
        if (userService.updateUser(user)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Удаляет пользователя
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        log.info("Удаление пользователя по id {}", id);
        User user = new User();
        user.setId(id);
        boolean isDeleted = userService.deleteUser(user);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает информацию о пользователе
     */
    @GetMapping
    public Optional<User> getUsersData(@RequestParam long userId) {
        log.info("Получение информации пользователя");

        return userService.getUserById(userId);
    }

    /**
     * Получает список пользователей
     */
    @GetMapping
    public List<User> getDataOfUsers() {
        log.info("Получение информации всех пользователей");

        return userService.getAllUsers();
    }
    
    /**
     * Добавляет смену
     */
    @PostMapping("/shifts")
    public ResponseEntity<?> addShift(@RequestBody Shift shift) {
        log.info("Добавление смены", shift);
        long id = shiftService.beginShift(shift);

        if (id != 0){
            return ResponseEntity.created(URI.create("/shifts/"+id)).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Обновляет информацию о смене
     */
    @PutMapping("/shifts")
    public ResponseEntity<?> updateShift(@RequestBody Shift shift) {
        log.info("Обновление информацию о смене");
        if (shiftService.finishShift(shift)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Удаляет смену
     */
    @DeleteMapping("/shifts/{id}")
    public ResponseEntity<?> deleteShift(@PathVariable long id) {
        log.info("Удаление смены по id {}", id);
        Shift shift = new Shift();
        shift.setId(id);
        boolean isDeleted = shiftService.deleteShift(shift);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает информацию о смене
     */
    @GetMapping("/shifts/{id}")
    public List<Shift> getShift(@PathVariable long id) {
        log.info("Получение инфорации о смене");

        return shiftService.getShiftById(id);
    }

    /**
     * Получает информацию о всех сменах пользователя
     */
    @GetMapping("{id}/shifts/")
    public List<Shift> getShifts(@PathVariable long id) {
        log.info("Получение инфорации о сменах");

        return shiftService.getAllShiftsOfUser(id);
    }

}
