package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.exceptions.UserNotFound;
import ru.sber.delivery.repositories.ShiftRepository;
import ru.sber.delivery.repositories.UserRepository;
import ru.sber.delivery.security.services.UserDetailsImpl;

import java.util.List;

/**
 * Реализация сервиса для работника ресторана
 */
@Service
public class RestaurantEmployeeServiceImpl implements RestaurantEmployeeService {

    private final UserRepository userRepository;

    @Autowired
    public RestaurantEmployeeServiceImpl(UserRepository userRepository, ShiftRepository shiftRepository) {
        this.userRepository = userRepository;
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

    @Override
    public boolean update(User user) {
        if (getUserIdSecurityContext() == user.getId()) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<User> findFreeCouriers() {
        return userRepository.findAllByStatus(EStatusCourier.FREE);
    }

    @Override
    public User findNearestFreeCourier() {
        return null;
    }

    @Override
    public boolean notifyCourier(long idOrder, long idUser) {
        return false;
    }

}
