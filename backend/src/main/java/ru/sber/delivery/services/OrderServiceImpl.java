package ru.sber.delivery.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.sber.delivery.order.OrderFeign;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Реализует логику работы с order-service
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderFeign orderFeign;

    public OrderServiceImpl(OrderFeign orderFeign) {
        this.orderFeign = orderFeign;
    }

    public ResponseEntity<List<?>> findActiveCourier() {
        return orderFeign.findActiveOrder();
    }

    @Override
    public ResponseEntity<?> updateOrderStatus(Object order) {
        return orderFeign.updateOrderStatus(order);
    }

    @Override
    public ResponseEntity<?> updateOrderCourierId(Object order) {
        return orderFeign.updateOrderCourier(order);
    }

    @Override
    public List<?> findAllActiveOrder() {
        return orderFeign.findActiveOrder().getBody();
    }

    @Override
    public Optional<?> findOrderById(long id) {
        if (orderFeign.getOrderById(id).hasBody()) {
            return Optional.of(Objects.requireNonNull(orderFeign.getOrderById(id).getBody()));
        }
        return Optional.empty();

    }

    @Override
    public List<?> findOrdersByCourierId(long id) {
        return orderFeign.getAllOrdersByCourierId(id).getBody();
    }
}
