package ru.sber.delivery.services;

import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Отвечает за работу администратора
 */
public interface AdministrationService {

    /**
     * Обновляет данные администратора
     * @param user новые данные администратора
     * @return true - в случае успеха
     */
    boolean update(User user);

    /**
     * Удаляет данные о пользователе
     * @param user - пользователь
     * @return true - в случае успеха
     */
    boolean delete(User user);

    /**
     * Возвращает информацию о пользователе по id
     * @param idUser - id пользователя
     * @return данные о пользователе
     */
    User getUser(long idUser);

    /**
     * Возвращает информацию о всех пользователях
     * @return список пользователей
     */
    List<User> getAllUsers();

    /**
     * Возвращает смены пользователя
     * @param idUser - id пользователя
     * @return - спискок смен
     */
    List<Shift> getShiftsUser(User  idUser);

    /**
     * Возвращает пользоватлей вышедших на смену в заданный день
     * @param dateShift - день смены
     * @return - Список пользователей
     */
    List<User> getUsersByShift(LocalDate dateShift);
}
