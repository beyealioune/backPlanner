package planner.com.planner.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import planner.com.planner.demo.model.Schedules;
import planner.com.planner.demo.model.User;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedules, Long> {
    List<Schedules> findByUser(User user);
    List<Schedules> findByIsAvailable(boolean isAvailable);
    List<Schedules> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}