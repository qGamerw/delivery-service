package ru.sber.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Notify;
import ru.sber.delivery.repositories.NotifyRepository;

import java.util.List;

@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    private final NotifyRepository notifyRepository;
    private final JwtService jwtService;

    @Autowired
    public NotifyServiceImpl(NotifyRepository notifyRepository, JwtService jwtService) {
        this.notifyRepository = notifyRepository;
        this.jwtService = jwtService;
    }

    @Override
    public List<Notify> findNotifyByUserId() {
        return notifyRepository.findAllByUser_Id(jwtService.getUserIdSecurityContext());
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
}
