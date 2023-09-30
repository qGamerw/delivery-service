package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.data.Coordinates;
import ru.sber.delivery.services.RestaurantEmployeeService;

import java.util.List;
import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов работника ресторана
 */
@Slf4j
@RestController
@RequestMapping("employees")
public class RestaurantEmployeeController {
    private final RestaurantEmployeeService restaurantEmployeeService;

    /**
     * Конструктор контроллера работника ресторана
     */
    @Autowired
    public RestaurantEmployeeController(RestaurantEmployeeService restaurantEmployeeService) {
        this.restaurantEmployeeService = restaurantEmployeeService;
    }

    /**
     * Обновляет информацию о работнике ресторана
     *
     * @param user - новая информация о пользователе
     * @return - результат запроса
     */
    @PutMapping
    public ResponseEntity<?> updateCourier(@RequestBody User user) {
        log.info("Обновление данных о работнике ресторана");

        if (restaurantEmployeeService.update(user)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Получает информацию о свободных курьерах
     * @return - список свободных курьеров
     */
    @GetMapping("/free-couriers")
    public ResponseEntity<List<User>> getFreeCouriers() {
        log.info("Получение информации о свободных курьерах");

        return ResponseEntity.ok().body(restaurantEmployeeService.findFreeCouriers());
        
    }

    /**
     * Получает информацию о ближайшем свободном курьере
     * @param coordinates - координаты ресторана
     * @return - результат запроса
     */
    @GetMapping("/nearest-free-courier")
    public ResponseEntity<?> getNearestFreeCourier(@RequestBody Coordinates coordinates) {
        log.info("Получение информации о ближайшем свободном курьере");
        Optional<User> optionalUser = restaurantEmployeeService
                                      .findNearestFreeCourier(coordinates.latitude(), coordinates.longitude());

        if(optionalUser.isPresent()){
            return ResponseEntity.ok().body(optionalUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    /**
     * Уведомляет курьера о заказе
     * @param idUser - индификатор курьера
     * @return - результат запроса
     */
    @PutMapping("/notify/{id}")
    public ResponseEntity<?> notifyCourier(@PathVariable("id") long idUser) {
        log.info("Уведомление курьера о заказе");
        
        if (restaurantEmployeeService.notifyCourier(idUser)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
