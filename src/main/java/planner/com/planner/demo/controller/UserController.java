package planner.com.planner.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import planner.com.planner.demo.model.Friend;
import planner.com.planner.demo.model.User;
import planner.com.planner.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Créer un utilisateur
    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // Récupérer tous les utilisateurs
    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        User user = userService.getUserById(userId);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable pour cet ID :: " + userId);
        }
        return ResponseEntity.ok().body(user);
    }
    

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails) throws EntityNotFoundException {
        final User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }


    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) {
    userService.deleteUser(userId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("supprimé", Boolean.TRUE);
    return response;
}

    

    // Ajouter un ami à un utilisateur
    @PostMapping("/{userId}/friends")
    public Friend createFriend(@PathVariable(value = "userId") Long userId,
                               @Valid @RequestBody Friend friend) throws EntityNotFoundException {
        return userService.addFriendToUser(userId, friend);
    }
    

}
