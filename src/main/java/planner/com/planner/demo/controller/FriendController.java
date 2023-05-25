package planner.com.planner.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import planner.com.planner.demo.service.FriendService;

@RestController
@RequestMapping("/users/{userId}/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;


    
    @GetMapping("/{id}")
    public ResponseEntity<Friend> getFriendById(@PathVariable(value = "id") Long friendId) {
        Optional<Friend> optionalFriend = friendService.getFriendsByFriendId(friendId);
        if (optionalFriend.isPresent()) {
            Friend friend = optionalFriend.get();
            return ResponseEntity.ok().body(friend);
        } else {
             return ResponseEntity.notFound().build();
        }
    }
    
    
        @PostMapping("/")
        public Friend createFriend(@Valid @RequestBody Friend friend) {
            return friendService.createFriend(friend);
        }
    
        @PutMapping("/{id}")
        public ResponseEntity<?> updateFriend(@PathVariable(value = "id") Long friendId,
                                               @Valid @RequestBody Friend friendDetails) {
            try {
                final Friend updatedFriend = friendService.updateFriend(friendId, friendDetails);
                return ResponseEntity.ok(updatedFriend);
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatus()).body(e.getMessage());
            }
        }
        

        
    
        @DeleteMapping("/{friendId}")
        public ResponseEntity<?> deleteFriend(@PathVariable(value = "friendId") Long friendId) {
            friendService.deleteFriend(friendId);
            return ResponseEntity.ok().build();
        }
        

        
    
}
