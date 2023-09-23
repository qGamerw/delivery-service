package ru.sber.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.EStatusCourier;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findByUsername(String username);
    List<User> findAllByStatus(EStatusCourier statusCourier);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
