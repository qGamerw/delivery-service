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

    public ResponseEntity<?> updateOrderStatus(Long id, Object order) {
        return orderFeign.updateOrderStatus(id, order);
    }


    public ResponseEntity<?> updateOrderCourierId(Object order) {
        return orderFeign.updateOrderCourier(order);
    }

    @Override
    public Page<?> findAllActiveOrdersByPage(int page, int pageSize) {
        return orderFeign.findAllActiveOrdersByPage(page, pageSize).getBody();
    }


    public Optional<?> findOrderById(long idOrder) {
        return Optional.of(Objects.requireNonNull(orderFeign.getOrderById(idOrder).getBody()));
    }

    @Override
    public Page<?> findOrdersByCourierId(int page, int pageSize) {
        return orderFeign.getAllOrdersByCourierId(getUserIdSecurityContext(), page, pageSize).getBody();
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
