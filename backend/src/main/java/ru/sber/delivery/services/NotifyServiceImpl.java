package ru.sber.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Notify;
import ru.sber.delivery.exceptions.UserNotFound;
import ru.sber.delivery.repositories.NotifyRepository;
import ru.sber.delivery.security.services.UserDetailsImpl;

import java.util.List;

@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    private final NotifyRepository notifyRepository;

    @Autowired
    public NotifyServiceImpl(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    @Override
    public List<Notify> findNotifyByUserId() {
        return notifyRepository.findAllByUser_Id(getUserIdSecurityContext());
    }

    @Override
    public boolean save(Notify notify) {
        log.info("Создание уведомления {}", notify);
        notifyRepository.save(notify);
        return true;
    }

    @Override
    public boolean update(Notify notify) {
        log.info("Обновление уведомления {}", notify);
        if(notifyRepository.existsById(notify.getId())){
            notifyRepository.save(notify);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Notify notify) {
        log.info("Удаление уведомления {}", notify);
        if(notifyRepository.existsById(notify.getId())){
            notifyRepository.delete(notify);
            return true;
        }
        return false;
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
