package ru.sber.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.exceptions.UserNotFound;
import ru.sber.delivery.repositories.ShiftRepository;
import ru.sber.delivery.security.services.UserDetailsImpl;

import java.time.LocalDateTime;
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
    public long save() {
        log.info("Смена создана");
        Shift shift = new Shift();
        shift.setBeginShift(LocalDateTime.now());
        User user = new User();
        user.setId(getUserIdSecurityContext());
        shift.setUser(user);
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
    public List<Shift> findAllShiftsByUser(long idUser) {
        log.info("Поиск смен пользователя");
        return shiftRepository.findAllByUserId(idUser);
    }

    /**
     * Возвращает id user из security context
     *
     * @return идентификатор пользователя
     */
    private long getUserIdSecurityContext() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getId();
        } else {
            throw new UserNotFound("Пользователь не найден");
        }
    }

}
