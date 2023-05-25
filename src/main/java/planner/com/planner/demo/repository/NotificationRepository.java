package planner.com.planner.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import planner.com.planner.demo.model.User;
import planner.com.planner.demo.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
    List<Notification> findByIsRead(boolean isRead);
}