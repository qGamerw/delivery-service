package ru.sber.delivery.services;

import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;

import java.util.ArrayList;

public interface ShiftService {

    long beginShift(Shift shift);

    boolean finishShift(Shift shift);

    ArrayList<Shift> getAllShiftsOfUser(long userId);

    boolean deleteShift(Shift shift);

    boolean updateAllShiftsOfUser(User user);

}
