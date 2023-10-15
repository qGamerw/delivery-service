package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
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
     * Возвращает заказы ожидающие доставки, ограниченные страницей
     *
     * @return список заказов
     */
    @GetMapping("/awaiting-delivery")
    public ResponseEntity<Page<?>> getActiveOrder(@RequestParam int page) {
        int pageSize = 10;
        return ResponseEntity.ok(orderService.findAllActiveOrdersByPage(page, pageSize));
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
     * @return заказ
     */
    @GetMapping("/{idOrder}")
    ResponseEntity<?> getOrderById() {
        return ResponseEntity.ok(orderService.findOrderById());
    }

    ;

    /**
     * Возвращает все заказы курьера
     *
     * @return заказы курьера
     */
    @GetMapping
    ResponseEntity<List<?>> getAllOrdersByCourierId() {
        return ResponseEntity.ok(orderService.findOrdersByCourierId());
    }
}
