package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.Shift;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.ERole;
import ru.sber.delivery.repositories.ShiftRepository;
import ru.sber.delivery.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Реализация сервиса для администратора
 */
@Service
public class AdministrationServiceImpl implements AdministrationService{

    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;

    @Autowired
    public AdministrationServiceImpl(UserRepository userRepository, ShiftRepository shiftRepository) {
        this.userRepository = userRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public boolean update(User user) {
       if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
            return true;
       }
       return false;
    }

    @Override
    public boolean delete(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findUser(long idUser) {
        return userRepository.findById(idUser);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().getRole() != ERole.ADMINISTRATION)
                .toList();
    }

    @Override
    public List<User> findUsersByShift(LocalDate dateShift) {
        return shiftRepository.findShiftsByBeginShift(dateShift)
                .stream()
                .filter(shift -> userRepository.existsById(shift.getUser().getId()))
                .map(Shift::getUser)
                .toList();
    }

}
