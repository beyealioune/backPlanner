package planner.com.planner.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import planner.com.planner.demo.repository.FriendRepository;
import planner.com.planner.demo.model.Friend;
import planner.com.planner.demo.model.User;


@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    public List<Friend> getFriendsByUserId(User userId) {
        return friendRepository.findByUserId(userId);
    }

    public Optional<Friend> getFriendsByFriendId(Long friendId) {
        return friendRepository.findById(friendId);
    }

    public Friend createFriend(Friend friend) {
        return friendRepository.save(friend);
    }

   

    public void deleteFriend(Long friendId) {
        Optional<Friend> friendOptional = friendRepository.findById(friendId);
        if (friendOptional.isEmpty()) {
            throw new IllegalArgumentException("Ami introuvable avec l'ID : " + friendId);
        }
        friendRepository.delete(friendOptional.get());
    }

    public Friend updateFriend(Long friendId, Friend friendDetails) {
        Optional<Friend> optionalFriend = friendRepository.findById(friendId);
        if (optionalFriend.isPresent()) {
            Friend friend = optionalFriend.get();
            friend.setFriend(friend.getFriend());
            friend.setFriend(friend.getUser());
            return friendRepository.save(friend);
        } else {
            return null;
        }
    }
    
}
