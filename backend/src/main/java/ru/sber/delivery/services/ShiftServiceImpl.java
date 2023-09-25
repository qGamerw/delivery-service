package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.repositories.ShiftRepository;

import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftServiceImpl(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public long save(Shift shift) {
        return shiftRepository.save(shift).getId();
    }

    @Override
    public boolean update(Shift shift) {
        if (shiftRepository.existsById(shift.getId())) {
            shiftRepository.save(shift);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Shift shift) {
        if (shiftRepository.existsById(shift.getId())) {
            shiftRepository.delete(shift);
            return true;
        }
        return false;
    }

    @Override
    public List<Shift> findAllShiftsByUser(long idUser) {
        return shiftRepository.findAllByUserId(idUser);
    }

}
