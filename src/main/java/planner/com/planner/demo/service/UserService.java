package planner.com.planner.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import planner.com.planner.demo.model.Friend;
import planner.com.planner.demo.model.User;
import planner.com.planner.demo.repository.FriendRepository;
import planner.com.planner.demo.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;





    


    @PostConstruct
    public void init() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    public User createUser(User user) {
        // User newUser = user ;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
       
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
 
    
    
    

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable pour cet ID :: " + id));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    

    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable pour cet ID :: " + id));
            userRepository.delete(user);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable pour cet ID :: " + id);
        }
    }
    
    

    public Friend addFriendToUser(Long id, Friend friend) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable pour cet ID :: " + id));
        friend.setUser(user);
        return friendRepository.save(friend);
    }
    
}
