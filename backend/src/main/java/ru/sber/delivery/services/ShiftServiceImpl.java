package ru.sber.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.repositories.ShiftRepository;

import java.util.List;

/**
 * Реализация сервиса смены
 */
@Slf4j
@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    @Autowired
    public ShiftServiceImpl(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    @Override
    public long save(Shift shift) {
        log.info("Смена создана");
        return shiftRepository.save(shift).getId();
    }

    @Override
    public boolean update(Shift shift) {
        log.info("Обновление смены");
        if (shiftRepository.existsById(shift.getId())) {
            log.info("Обновление успешно");
            shiftRepository.save(shift);
            return true;
        }
        log.warn("Обновление ровалено");
        return false;
    }

    @Override
    public boolean delete(Shift shift) {
        log.info("Удапление смены");
        if (shiftRepository.existsById(shift.getId())) {
            log.info("Удапление успешно");
            shiftRepository.delete(shift);
            return true;
        }
        log.warn("Удапление провалено");
        return false;
    }

    @Override
    public List<Shift> findAllShiftsByUser(long idUser) {
        log.info("поиск смен пользователя");
        return shiftRepository.findAllByUserId(idUser);
    }

}
