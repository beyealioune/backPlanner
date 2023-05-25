package planner.com.planner.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import planner.com.planner.demo.model.Schedules;
import planner.com.planner.demo.service.ScheduleService;

@RestController
@RequestMapping("/schedules")
public class SchedulesController {

    @Autowired
    private ScheduleService scheduleService;

    // Récupérer tous les horaires disponibles
    @GetMapping("/available")
    public List<Schedules> getAvailableSchedules() {
        return scheduleService.getAvailableSchedules();
    }

    // Récupérer les horaires pour un utilisateur spécifique
    @GetMapping("/{userId}")
    public ResponseEntity<Schedules> getSchedulesByUserId(@PathVariable(value = "userId") Long userId) {
        Optional<Schedules> optionalSchedule = scheduleService.getSchedulesByUserId(userId);
        if(optionalSchedule.isPresent()) {
            Schedules schedule = optionalSchedule.get();
            return ResponseEntity.ok(schedule);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Récupérer les horaires entre deux dates et heures
    @GetMapping("/{start}/{end}")
    public List<Schedules> getSchedulesByTime(@PathVariable(value = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                              @PathVariable(value = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return scheduleService.getSchedulesByTime(start, end);
    }

    // Créer un nouvel horaire
    @PostMapping("/")
    public Schedules createSchedule(@Valid @RequestBody Schedules schedule) {
        return scheduleService.createSchedule(schedule);
    }

}
