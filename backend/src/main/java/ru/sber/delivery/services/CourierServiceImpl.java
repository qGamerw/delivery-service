package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.exceptions.UserNotFound;
import ru.sber.delivery.repositories.UserRepository;
import ru.sber.delivery.security.services.UserDetailsImpl;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Реализация сервиса для курьера
 */
@Service
public class CourierServiceImpl implements CourierService {

    private final UserRepository userRepository;
    private final AdministrationService administrationService;

    @Autowired
    public CourierServiceImpl(UserRepository userRepository, AdministrationService administrationService) {
        this.userRepository = userRepository;
        this.administrationService = administrationService;
    }

    @Override
    public Optional<User> findUser() {
        return userRepository.findById(getUserIdSecurityContext());
    }

    @Override
    public boolean update(User user) {
        return administrationService.update(user);
    }

    @Override
    public boolean updateUserStatus(EStatusCourier statusCourier) {
        Optional<User> user = userRepository.findById(getUserIdSecurityContext());
        if (user.isPresent()) {
            user.get().setStatus(statusCourier);
            return update(user.get());
        }
        return false;
    }

    @Override
    public boolean updateUserCoordinates(BigDecimal latitude, BigDecimal longitude) {
        Optional<User> user = userRepository.findById(getUserIdSecurityContext());
        if (user.isPresent()) {
            user.get().setLatitude(latitude);
            user.get().setLongitude(longitude);
            return update(user.get());
        }
        return false;
    }

    /**
     * Возвращает id user из security context
     *
     * @return индификатор пользователя
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
