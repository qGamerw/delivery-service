package ru.sber.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.repositories.ShiftRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация сервиса смены
 */
@Slf4j
@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final JwtService jwtService;

    @Autowired
    public ShiftServiceImpl(ShiftRepository shiftRepository, JwtService jwtService) {
        this.shiftRepository = shiftRepository;
        this.jwtService = jwtService;
    }

    @Override
    public long save() {
        log.info("Смена создана");
        Shift shift = new Shift();
        shift.setBeginShift(LocalDateTime.now());
        User user = new User();
        user.setId(jwtService.getUserIdSecurityContext());
        shift.setUser(user);
        return shiftRepository.save(shift).getId();
    }

    @Override
    public boolean update(Shift shift) {
        log.info("Обновление смены");
        User user = new User();
        user.setId(jwtService.getUserIdSecurityContext());
        if (shiftRepository.existsById(shift.getId())) {
            log.info("Обновление успешно");
            shift.setUser(user);
            shiftRepository.save(shift);
            return true;
        }
        log.warn("Обновление ровалено");
        return false;
    }

    @Override
    public boolean delete(long idShift) {
        log.info("Удапление смены");
        if (shiftRepository.existsById(idShift)) {
            log.info("Удапление успешно");
            shiftRepository.deleteById(idShift);
            return true;
        }
        log.warn("Удаление провалено");
        return false;
    }

    @Override
    public List<Shift> findAllShiftsByUser(String idUser) {
        log.info("Поиск смен пользователя");
        return shiftRepository.findAllByUserId(idUser);
    }


}
