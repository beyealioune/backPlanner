package planner.com.planner.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import planner.com.planner.demo.model.Schedules;
import planner.com.planner.demo.repository.ScheduleRepository;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Optional<Schedules> getSchedulesByUserId(Long userId) {
        return scheduleRepository.findById(userId);
    }

    public List<Schedules> getAvailableSchedules() {
        return scheduleRepository.findByIsAvailable(true);
    }

    public List<Schedules> getSchedulesByTime(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByStartTimeBetween(start, end);
    }

    public Schedules createSchedule(Schedules schedule) {
        return scheduleRepository.save(schedule);
    }

}
