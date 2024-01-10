package ru.sber.delivery.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Notify;
import ru.sber.delivery.entities.Role;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.ERole;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.repositories.RoleRepository;
import ru.sber.delivery.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для курьера
 */
@Slf4j
@Service
public class CourierServiceImpl implements CourierService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NotifyService notifyService;
    private final OrderService orderService;
    private final AdministrationService administrationService;
    private final JwtService jwtService;

    @Autowired
    public CourierServiceImpl(UserRepository userRepository, RoleRepository roleRepository, NotifyService notifyService, OrderService orderService, AdministrationService administrationService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.notifyService = notifyService;
        this.orderService = orderService;
        this.administrationService = administrationService;
        this.jwtService = jwtService;
    }

    @Override
    public boolean addUserById(String userId) {
        User user = new User(userId);
        
        Role userRole = roleRepository.findByRole(ERole.COURIER)
                    .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        user.setRole(userRole);
        log.info("Регистрация курьера");
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findUser() {
        log.info("Поиск информации о курьере (сторона курьера)");
        return userRepository.findById(jwtService.getUserIdSecurityContext());
    }

    @Override
    public boolean update(User user) {
        log.info("Обновление информации о пользователе");
        return administrationService.update(user);
    }

    @Override
    public boolean updateUserStatus(EStatusCourier statusCourier) {
        log.info("Обновление статуса пользователя");
        Optional<User> user = userRepository.findById(jwtService.getUserIdSecurityContext());
        if (user.isPresent()) {
            log.info("Обновление успешно");
            user.get().setStatus(statusCourier);
            return update(user.get());
        }
        log.warn("Обновление провалено");
        return false;
    }

    @Override
    public boolean updateUserCoordinates(BigDecimal latitude, BigDecimal longitude) {
        log.info("Обновление координат пользователя");
        Optional<User> user = userRepository.findById(jwtService.getUserIdSecurityContext());
        if (user.isPresent()) {
            log.info("Обновление успешно");
            user.get().setLatitude(latitude);
            user.get().setLongitude(longitude);
            return update(user.get());
        }
        log.warn("Обновление провалено");
        return false;
    }

    @Override
    public List<?> notifyCourier() {
        List<Notify> notifies = notifyService.findNotifyByUserId();
        log.info("Список уведомлений: {}", notifies);
        return notifies.stream().map(notify -> {
                    notifyService.delete(notify);
                    return orderService.findOrderById(notify.getOrderId());
                }
        ).toList();
    }

}
