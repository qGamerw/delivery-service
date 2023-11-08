package ru.sber.delivery.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final KafkaTemplate<String, Order> kafkaUpdateStatusCourierTemplate;


    @Autowired
    public OrderController(OrderService orderService,
                           KafkaTemplate<String, Order> kafkaUpdateStatusCourierTemplate) {
        this.orderService = orderService;
        this.kafkaUpdateStatusCourierTemplate = kafkaUpdateStatusCourierTemplate;

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
     * Возвращает заказы которые доставляет курьер 
     *
     * @return заказы курьера
     */
    @GetMapping("/delivering")
    ResponseEntity<List<?>> getDeliveringOrdersByCourierId() {
        return ResponseEntity.ok(orderService.getOrdersIsDelivering());
    }

    /**
     * Устанавливает курьера на заказ
     *
     * @param order содержит id курьера и id заказа
     */
    @PutMapping("/courier")
    public ResponseEntity<?> updateOrderCourier(@RequestBody Object order) {
        log.info("Обновление курьера на заказ {}", order);
        orderService.updateOrderCourierId(order);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет статус заказа
     *
     */
    @PutMapping("/{idOrder}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("idOrder") Long id, @RequestBody OrderStatus orderStatus) {
        log.info("обновление статуса заказа с id{}", id);
        return orderService.updateOrderStatus(id, orderStatus);
    }

    /**
     * Возвращает заказ по id
     *
     * @return заказ
     */
    @GetMapping("/{idOrder}")
    ResponseEntity<?> getOrderById(@PathVariable long idOrder) {
        return ResponseEntity.ok(orderService.findOrderById(idOrder));
    }

    /**
     * Возвращает все заказы курьера
     *
     * @return заказы курьера
     */
    @GetMapping
    ResponseEntity<Page<?>> getAllOrdersByCourierId(@RequestParam int page) {
        int pageSize = 10;
        return ResponseEntity.ok(orderService.findOrdersByCourierId(page, pageSize));
    }
}
