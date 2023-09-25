package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.data.Coordinates;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.services.CourierService;
import ru.sber.delivery.services.ShiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Класс отвечающий за обработку запросов о сменах
 */
@Slf4j
@RestController
@RequestMapping("shifts")
public class ShiftController {
    private ShiftService shiftService;

    /**
     * Конструктор контроллера смен
     */
    @Autowired
    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    /**
     * Добавляет смену
     */
    @PostMapping
    public ResponseEntity<?> addShift(@RequestBody Shift shift) {
        log.info("Добавление смены");
        long id = shiftService.save(shift);

        if (id != 0){
            return ResponseEntity.created(URI.create("/shifts/"+id)).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Обновляет информацию о смене
     */
    @PutMapping
    public ResponseEntity<?> updateCourier(@RequestBody Shift shift) {
        log.info("Обновление данных о смене");

        if (shiftService.update(shift)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Удаляет смену
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCourier(@RequestBody Shift shift) {
        log.info("Удаление смены");
        boolean isDeleted = shiftService.delete(shift);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает информацию о всех сменах курьера
     */
    @GetMapping("/courier/{id}")
    public List<Shift> getAllCouriersData(@PathVariable long idUser) {
        log.info("Получение информации о всех сменах курьера");
        List<Shift> shiftList = shiftService.findAllShiftsByUser(idUser);

        return shiftList;
        
    }



}
