package ru.sber.delivery.services;

import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;

import java.util.List;

public interface ShiftService {

    long save(Shift shift);

    boolean update(Shift shift);

    boolean delete(Shift shift);

    /**
     * Возвращает смены пользователя
     * @param idUser - id пользователя
     * @return - спискок смен
     */
    List<Shift> findAllShiftsByUser(long idUser);

}
