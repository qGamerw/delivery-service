package ru.sber.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.repositories.ShiftRepository;
import ru.sber.delivery.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShiftRepository shiftRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ShiftRepository shiftRepository) {
        this.userRepository = userRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public long signUp(User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public Optional<User> getUserById(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean checkUserExistance(long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public boolean deleteUser(User user) {
        if (checkUserExistance(user.getId())) {
            shiftRepository.updateAllByUser(user.getId());
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if (checkUserExistance(user.getId())) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }


}
