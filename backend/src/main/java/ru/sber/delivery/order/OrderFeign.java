package ru.sber.delivery.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Отвечает за взаимодействие с order-service
 */
@FeignClient(name = "orderService", url="localhost:8080/")
public interface OrderFeign {
    @PutMapping("orders/courier")
    ResponseEntity<?> updateOrderCourier(@RequestBody Object order);
    @GetMapping("orders/awaiting-delivery")
    ResponseEntity<List<?>> findActiveOrder();
    @GetMapping("orders/delivering")
    ResponseEntity<List<?>> findAllActiveOrderForCourier();
    @GetMapping("orders/{idOrder}")
    ResponseEntity<?> getOrderById(@PathVariable("idOrder") long id);
    @GetMapping("orders/courier/{idCourier}")
    ResponseEntity<List<?>> getAllOrdersByCourierId(@PathVariable("idCourier") long id);
    @GetMapping("orders/delivering/courier/{idCourier}")
    ResponseEntity<List<?>> getOrdersIsDelivering(@PathVariable("idCourier") long id);
    @PutMapping("orders")
    ResponseEntity<?> updateOrderStatus(@RequestBody Object order);
}
