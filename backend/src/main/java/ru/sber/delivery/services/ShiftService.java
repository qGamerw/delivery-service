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
     * @return - id смены
     */
    long save();

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
     * @param idShift - индификатор смены
     * @return - true  случае успеха
     */
    boolean delete(long idShift);

    /**
     * Возвращает смены пользователя
     *
     * @param idUser - id пользователя
     * @return - спискок смен
     */
    List<Shift> findAllShiftsByUser(long idUser);

}
