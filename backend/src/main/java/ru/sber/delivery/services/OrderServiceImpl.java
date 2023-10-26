package ru.sber.delivery.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.delivery.exceptions.UserNotFound;
import ru.sber.delivery.order.OrderFeign;
import ru.sber.delivery.security.services.UserDetailsImpl;

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

    public ResponseEntity<?> updateOrderStatus(Object order) {
        return orderFeign.updateOrderStatus(order);
    }


    public ResponseEntity<?> updateOrderCourierId(Object order) {
        return orderFeign.updateOrderCourier(order);
    }

    @Override
    public Page<?> findAllActiveOrdersByPage(int page, int pageSize) {
        return orderFeign.findAllActiveOrdersByPage(page, pageSize).getBody();
    }


    public Optional<?> findOrderById() {
        if (orderFeign.getOrderById(getUserIdSecurityContext()).hasBody()) {
            return Optional.of(Objects.requireNonNull(orderFeign.getOrderById(getUserIdSecurityContext()).getBody()));
        }
        return Optional.empty();

    }

    @Override
    public List<?> findOrdersByCourierId() {
        return orderFeign.getAllOrdersByCourierId(getUserIdSecurityContext()).getBody();
    }

    @Override
    public List<?> getOrdersIsDelivering() {
        return orderFeign.getOrdersIsDelivering(getUserIdSecurityContext()).getBody();
    }

    /**
            * Возвращает id user из security context
     *
             * @return идентификатор пользователя
     */
    private long getUserIdSecurityContext() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getId();
        } else {
            throw new UserNotFound("Пользователь не найден");
        }
    }
}
