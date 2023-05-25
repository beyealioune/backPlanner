package planner.com.planner.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import planner.com.planner.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByIsAdmin(boolean isAdmin);
    List<User> findByIsActive(boolean isActive);
}