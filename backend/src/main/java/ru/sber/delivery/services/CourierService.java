package ru.sber.delivery.services;

import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.EStatusCourier;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Отвечает за работу курьера
 */
public interface CourierService {

    /**
     * Веозвращает информацию о курьере
     *
     * @return информация о курьере
     */
    Optional<User> findUser();

    /**
     * Обновляет информацию о курьере
     * @param user - новые данные курьера
     * @return true в случае успеха
     */
    boolean update(User user);

    /**
     * Обновляет статус курьера
     * @param statusCourier - новый статус
     * @return
     */
    boolean updateStatusUser(EStatusCourier statusCourier);

    /**
     * Обновляет местоположение курьера
     * @param latitude - широта
     * @param longitude - долгота
     * @return
     */
    boolean updateCoordinateUser(BigDecimal latitude, BigDecimal longitude);

}
