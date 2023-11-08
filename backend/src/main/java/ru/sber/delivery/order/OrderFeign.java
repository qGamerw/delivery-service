package ru.sber.delivery.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Отвечает за взаимодействие с order-service
 */
@FeignClient(name = "orderService", url="localhost:8083/")
public interface OrderFeign {
    @PutMapping("orders/courier")
    ResponseEntity<?> updateOrderCourier(@RequestBody Object order);
    @GetMapping("orders/awaiting-delivery/by-page")
    ResponseEntity<Page<?>> findAllActiveOrdersByPage(@RequestParam int page, @RequestParam int pageSize);
    @GetMapping("orders/{idOrder}")
    ResponseEntity<?> getOrderById(@PathVariable("idOrder") long id);
    @GetMapping("orders/courier/{idCourier}")
    ResponseEntity<Page<?>> getAllOrdersByCourierId(@PathVariable("idCourier") long id, @RequestParam int page, @RequestParam int pageSize);
    @GetMapping("orders/delivering/courier/{idCourier}")
    ResponseEntity<List<?>> getOrdersIsDelivering(@PathVariable("idCourier") long id);
    @PutMapping("orders/{idOrder}")
    ResponseEntity<?> updateOrderStatus(@PathVariable("idOrder") long id, @RequestBody Object order);
}
