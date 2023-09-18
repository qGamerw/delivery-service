package ru.sber.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.delivery.entities.Shift;

import java.util.ArrayList;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    ArrayList<Shift> findAllByUserId(long userId);

    @Query("UPDATE shifts set user_id = NULL where id in (select id from shifts where user_id = ?1)")
    boolean updateAllByUser(long user_id);

}
