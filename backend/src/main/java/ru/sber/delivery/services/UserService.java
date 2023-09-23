package ru.sber.delivery.services;

import ru.sber.delivery.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    long signUp(User user);

    Optional<User> getUserById(long userId);

    boolean checkUserExistance(long userId);

    boolean deleteUser(User user);

    boolean updateUser(User user);
    List<User> getAllUsers();

    Optional<User> findByEmailAndPassword(String email, String password);
}
