package ru.sber.delivery.services;

import org.springframework.stereotype.Service;
import ru.sber.delivery.entities.User;

import java.util.Optional;

public interface UserService {
    long signUp(User user);

    Optional<User> getUserById(long userId);

    boolean checkUserExistance(long userId);

    boolean deleteUser(User user);

    Optional<User> findByEmailAndPassword(String email, String password);
}
