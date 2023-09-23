package ru.sber.delivery.services;

import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface ShiftService {

    long beginShift(Shift shift);

    boolean finishShift(Shift shift);

    List<Shift> getAllShiftsOfUser(long userId);

    boolean deleteShift(Shift shift);

    boolean updateAllShiftsOfUser(User user);

}
