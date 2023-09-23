package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.repositories.ShiftRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftServiceImpl(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public long beginShift(Shift shift) {
        return shiftRepository.save(shift).getId();
    }

    @Override
    public boolean finishShift(Shift shift) {
        shiftRepository.save(shift);
        return true;
    }

    @Override
    public List<Shift> getAllShiftsOfUser(long userId) {
        return shiftRepository.findAllByUserId(userId);
    }

    @Override
    public boolean deleteShift(Shift shift) {
        shiftRepository.delete(shift);
        return true;
    }

    @Override
    public boolean updateAllShiftsOfUser(User user) {

        return false;
    }
}
