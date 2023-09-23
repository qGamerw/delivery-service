package ru.sber.delivery.services;

import ru.sber.delivery.entities.User;

import java.util.List;

/**
 * Отвечает за работу работника ресторана
 */
public interface RestaurantEmployeeService {

    /**
     * Возвращает свободных курьеров
     * @return список курьеров
     */
    List<User> getFreeCouriers();

    /**
     * Возвращает ближайшего свободного курьера
     * @return ближайший курьер
     */
    User getNearestFreeCourier();

    /**
     * Уведомляет курьера о заказе
     * @param idOrder - номер заказа
     * @param idUser - номер пользователя которого требуется уведомить
     * @return true в случае успеха
     */
    boolean notifyCourier(long idOrder, long idUser);

}
