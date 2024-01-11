package ru.sber.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.delivery.entities.User;
import ru.sber.delivery.entities.enum_model.EStatusCourier;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByStatus(EStatusCourier statusCourier);
    List<User> findUserByStatus(EStatusCourier status);

}
