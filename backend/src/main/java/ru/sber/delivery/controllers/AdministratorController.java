package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.services.AdministrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

/**
 * Класс отвечающий за обработку запросов администраторов
 */
@Slf4j
@RestController
@RequestMapping("administrators")
public class AdministratorController {
    private AdministrationService administratorService;

    /**
     * Конструктор контроллера администраторов
     */
    @Autowired
    public AdministratorController(AdministrationService administratorService) {
        this.administratorService = administratorService;
    }

    /**
     * Получает информацию о курьере
     */
    @GetMapping("/courier/{id}")
    public ResponseEntity<?> getCouriersData(@PathVariable long idUser) {
        log.info("Получение информации о курьере");
        Optional<User> optionalUser = administratorService.findUser(idUser);

        if(optionalUser.isPresent()){
            return ResponseEntity.ok().body(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    /**
     * Обновляет информацию о курьере
     */
    @PutMapping
    public ResponseEntity<?> updateCourier(@RequestBody User user) {
        log.info("Обновление данных о курьере");

        if (administratorService.update(user)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Удаляет курьера
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCourier(@RequestBody User user) {
        log.info("Удаление курьера");
        boolean isDeleted = administratorService.delete(user);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает информацию о всех курьерах
     */
    @GetMapping("/all-couriers")
    public List<User> getAllCouriersData() {
        log.info("Получение информацию о курьерах");
        List<User> userList = administratorService.findAllUsers();

        return userList;
        
    }

    /**
     * Получает информацию о всех курьерах вышедших на смену в заданный день
     */
    @GetMapping("/all-couriers/by-date")
    public List<User> getAllShifts(@RequestBody LocalDate shiftDate) {
        log.info("Получение информации о всех пользователях вышедших на смену в заданный день");
        List<User> userList = administratorService.findUsersByShift(shiftDate);

        return userList;
        
    }


}
