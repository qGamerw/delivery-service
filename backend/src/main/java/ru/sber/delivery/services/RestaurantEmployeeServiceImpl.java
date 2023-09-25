package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Role;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.ERole;
import ru.sber.delivery.entities.enum_model.EStatusCourier;
import ru.sber.delivery.exceptions.UserNotFound;
import ru.sber.delivery.repositories.RoleRepository;
import ru.sber.delivery.repositories.UserRepository;
import ru.sber.delivery.security.services.UserDetailsImpl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работника ресторана
 */
@Service
public class RestaurantEmployeeServiceImpl implements RestaurantEmployeeService {

    private final UserRepository userRepository;
    private final AdministrationService administrationService;
    private final RoleRepository roleRepository;

    @Autowired
    public RestaurantEmployeeServiceImpl(UserRepository userRepository, AdministrationService administrationService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.administrationService = administrationService;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean update(User user) {
        return administrationService.update(user);
    }

    @Override
    public List<User> findFreeCouriers() {
        return userRepository.findAllByStatus(EStatusCourier.FREE);
    }

    @Override
    public Optional<User> findNearestFreeCourier(BigDecimal restaurantLatitude, BigDecimal restaurantLongitude) {
        Optional<Role> role = roleRepository.findByName(ERole.COURIER);
        if (role.isPresent()) {
            List<User> freeCouriers = userRepository.findUserByRoleAndStatus(role.get(), EStatusCourier.FREE);
            User nearestCourier = freeCouriers
                    .stream()
                    .min(Comparator.comparingDouble(user -> haversineDistance(user.getLongitude().doubleValue(), user.getLatitude().doubleValue(),
                            restaurantLongitude.doubleValue(), restaurantLatitude.doubleValue())))
                    .orElse(null);
            return Optional.ofNullable(nearestCourier);
        }
        return Optional.empty();
    }

    private double haversineDistance(double lon1, double lat1, double lon2, double lat2) {
        final double R = 6371.0;

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    @Override
    public boolean notifyCourier(long idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.isPresent()) {
            user.get().setNotify(true);
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
