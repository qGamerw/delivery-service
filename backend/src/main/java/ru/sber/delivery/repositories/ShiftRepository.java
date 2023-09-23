package ru.sber.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sber.delivery.entities.Shift;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findAllByUserId(long userId);

    @Query("UPDATE shifts set user_id = NULL where id in (select id from shifts where user_id = ?1)")
    boolean updateAllByUser(long user_id);

    default List<Shift> findShiftsByBeginShift(LocalDate beginShift) {
        return findShiftsByBeginShiftBetween(beginShift.atStartOfDay(), beginShift.plusDays(1).atStartOfDay());
    }
    List<Shift> findShiftsByBeginShiftBetween(LocalDateTime from, LocalDateTime to);
}
