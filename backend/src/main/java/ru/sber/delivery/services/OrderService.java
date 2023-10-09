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
     * Ищет заказ по id
     *
     * @param id идентификатор заказа
     * @return заказ
     */
    Optional<?> findOrderById(long id);
    /**
     * Возвращает все заказы которые брал курьер
     *
     * @param id идентификатор курьера
     * @return список заказов курьера
     */
    List<?> findOrdersByCourierId(long id);
}
