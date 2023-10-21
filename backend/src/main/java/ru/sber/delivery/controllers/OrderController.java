package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.sber.delivery.models.Order;
import ru.sber.delivery.models.OrderStatus;
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
    private KafkaTemplate<String, OrderStatus> kafkaOrderStatusTemplate;
    private KafkaTemplate<String, Order> kafkaOrderCourierTemplate;

    public OrderController(OrderService orderService, KafkaTemplate<String, OrderStatus> kafkaOrderStatusTemplate,
                           KafkaTemplate<String, Order> kafkaOrderCourierTemplate) {
        this.orderService = orderService;
        this.kafkaOrderStatusTemplate = kafkaOrderStatusTemplate;
        this.kafkaOrderCourierTemplate = kafkaOrderCourierTemplate;
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
    public ResponseEntity<?> updateOrderCourier(@RequestBody Order order) {
        kafkaOrderCourierTemplate.send("courier_order", order);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет статус заказа
     *
     */
    @PutMapping
    public ResponseEntity<?> updateOrderStatus(@RequestBody OrderStatus order) {
        kafkaOrderStatusTemplate.send("update_courier_order", order);
        return ResponseEntity.ok().build();
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
