package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.data.Coordinates;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.services.AdministrationService;
import ru.sber.delivery.services.CourierService;
import ru.sber.delivery.services.RestaurantEmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

/**
 * Класс отвечающий за обработку запросов работника ресторана
 */
@Slf4j
@RestController
@RequestMapping("employees")
public class RestaurantEmployeeController {
    private RestaurantEmployeeService restaurantEmployeeService;

    /**
     * Конструктор контроллера работника ресторана
     */
    @Autowired
    public RestaurantEmployeeController(RestaurantEmployeeService restaurantEmployeeService) {
        this.restaurantEmployeeService = restaurantEmployeeService;
    }

    /**
     * Обновляет информацию о работнике ресторана
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
     */
    @GetMapping("/free-couriers")
    public List<User> getFreeCouriers() {
        log.info("Получение информации о свободных курьерах");
        List<User> userList = restaurantEmployeeService.findFreeCouriers();

        return userList;
        
    }

    /**
     * Получает информацию о ближайшем свободном курьере
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
     */
    @PutMapping("/notify/{id}")
    public ResponseEntity<?> notifyCourier(@PathVariable long idUser) {
        log.info("Уведомление курьера о заказе");
        
        if (restaurantEmployeeService.notifyCourier(idUser)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
