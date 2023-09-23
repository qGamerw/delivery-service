package ru.sber.delivery.services;

import ru.sber.delivery.entities.User;

import java.util.List;

/**
 * Отвечает за работу работника ресторана
 */
public interface RestaurantEmployeeService {

    /**
     * Обновляет данные пользователя
     * @param user новые данные администратора
     * @return true - в случае успеха
     */
    boolean update(User user);
    /**
     * Возвращает свободных курьеров
     * @return список курьеров
     */
    List<User> findFreeCouriers();

    /**
     * Возвращает ближайшего свободного курьера
     * @return ближайший курьер
     */
    User findNearestFreeCourier();

    /**
     * Уведомляет курьера о заказе
     * @param idOrder - номер заказа
     * @param idUser - номер пользователя которого требуется уведомить
     * @return true в случае успеха
     */
    boolean notifyCourier(long idOrder, long idUser);

}
