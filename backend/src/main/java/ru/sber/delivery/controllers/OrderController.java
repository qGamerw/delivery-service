package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.delivery.services.OrderService;

import java.util.List;

/**
 * Получает Rest запросы к сервису заказов
 */
@Slf4j
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Возвращает заказы ожидающие доставки
     *
     * @return список заказов
     */
    @GetMapping("/awaiting-delivery")
    public ResponseEntity<List<?>> getActiveOrder() {
        return ResponseEntity.ok(orderService.findAllActiveOrder());
    }

    /**
     * Устанавливает курьера на заказ
     *
     * @param order содержит id курьера и id заказа
     */
    @PutMapping("/courier")
    public ResponseEntity<?> updateOrderCourier(@RequestBody Object order) {
        return orderService.updateOrderCourierId(order);
    }

    /**
     * Обновляет статус заказа
     *
     * @param order содержит id заказа и новый статус
     */
    @PutMapping
    public ResponseEntity<?> updateOrderStatus(@RequestBody Object order) {
        return orderService.updateOrderStatus(order);
    }

    /**
     * Возвращает заказ по id
     *
     * @param id заказа
     * @return заказ
     */
    @GetMapping("/{idOrder}")
    ResponseEntity<?> getOrderById(@PathVariable("idOrder") long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    ;

    /**
     * Возвращает все заказы курьера
     *
     * @param id курьера
     * @return заказы курьера
     */
    @GetMapping("/courier/{idCourier}")
    ResponseEntity<List<?>> getAllOrdersByCourierId(@PathVariable("idCourier") long id) {
        return ResponseEntity.ok(orderService.findOrdersByCourierId(id));
    }
}
