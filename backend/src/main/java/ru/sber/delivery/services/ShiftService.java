package ru.sber.delivery.services;

import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;

import java.util.List;

/**
 * Отвечает за смены
 */
public interface ShiftService {

    /**
     * Создает новую смену курьера
     *
     * @param shift - смена
     * @return - id смены
     */
    long save(Shift shift);

    /**
     * Заканчивает смену курьера
     *
     * @param shift - смена курьера
     * @return - true  случае успеха
     */
    boolean update(Shift shift);

    /**
     * Удаляет смену
     *
     * @param shift - смена
     * @return - true  случае успеха
     */
    boolean delete(Shift shift);

    /**
     * Возвращает смены пользователя
     *
     * @param idUser - id пользователя
     * @return - спискок смен
     */
    List<Shift> findAllShiftsByUser(long idUser);

}
