package ru.sber.delivery.services;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Получает информацию об order
 */
public interface OrderService {
    /**
     * Обновляет статус заказа
     *
     * @param order заказ
     * @return ответ
     */
    ResponseEntity<?> updateOrderStatus(Object order);
    /**
     * Устанавливает курьера на заказ
     * @param order заказ
     * @return ответ
     */
    ResponseEntity<?> updateOrderCourierId(Object order);
    /**
     * Ищет список заказов которые готовятся или уже готовы, но не доставляются
     *
     * @return список заказов
     */
    List<?> findAllActiveOrder();
    /**
     * Ищет список заказов которые доставляются
     *
     * @return список заказов
     */
    List<?> findAllActiveOrderForCourier();

    /**
     * Ищет заказ по id
     *
     * @return заказ
     */
    Optional<?> findOrderById();
    /**
     * Возвращает все заказы которые брал курьер
     *
     * @return список заказов курьера
     */
    List<?> findOrdersByCourierId();

    List<?> getOrdersIsDelivering();
}
