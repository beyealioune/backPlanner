package planner.com.planner.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import planner.com.planner.demo.model.User;
import planner.com.planner.demo.model.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserId(User user);
    List<Friend> findById(User friend);
}