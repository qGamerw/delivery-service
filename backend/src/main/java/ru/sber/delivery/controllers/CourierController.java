package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.data.Coordinates;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.services.CourierService;

import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов курьеров
 */
@Slf4j
@RestController
@RequestMapping("couriers")
public class CourierController {
    private final CourierService courierService;

    /**
     * Конструктор контроллера курьеров
     */
    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    /**
     * Получает информацию о курьер
     */
    @GetMapping
    public ResponseEntity<?> getCouriersData() {
        log.info("Получение информации о курьере");
        Optional<User> optionalUser = courierService.findUser();

        if(optionalUser.isPresent()){
            return ResponseEntity.ok().body(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    /**
     * Обновляет информацию о курьере
     *
     * @param user - новая информация о пользователе
     */
    @PutMapping
    public ResponseEntity<?> updateCourier(@RequestBody User user) {
        log.info("Обновление данных о курьере");

        if (courierService.update(user)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Обновляет статус курьера
     * @param statusCourier - новый статус курьера
     */
    @PutMapping("/status")
    public ResponseEntity<?> updateCourierStatus(@RequestBody String statusCourier) {
        log.info("Обновление данных статуса о курьере {}",statusCourier);

        if (courierService.updateUserStatus(EStatusCourier.valueOf(statusCourier))){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Обновляет местоположение курьера
     *
     * @param coordinates - новые координаты курьера
     */
    @PutMapping("/coordinates")
    public ResponseEntity<?> updateCourierCoordinates(@RequestBody Coordinates coordinates) {
        log.info("Обновление данных местоположения о курьере");
        
        if (courierService.updateUserCoordinates(coordinates.latitude(), coordinates.longitude())){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }



}
