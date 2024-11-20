package com.nulab.howudonodocker.service;

import com.nulab.howudonodocker.model.Friendship;
import com.nulab.howudonodocker.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    // Send a friend request
    public Friendship sendFriendRequest(String senderEmail, String receiverEmail) {
        if (friendshipRepository.findBySenderEmailAndReceiverEmail(senderEmail, receiverEmail).isPresent()) {
            throw new RuntimeException("Friend request already exists.");
        }
        Friendship friendship = new Friendship();
        friendship.setSenderEmail(senderEmail);
        friendship.setReceiverEmail(receiverEmail);
        friendship.setAccepted(false);
        return friendshipRepository.save(friendship);
    }

    // Accept a friend request
    public Friendship acceptFriendRequest(String senderEmail, String receiverEmail) {
        Optional<Friendship> optionalFriendship = friendshipRepository.findBySenderEmailAndReceiverEmail(senderEmail, receiverEmail);
        if (optionalFriendship.isEmpty()) {
            throw new RuntimeException("Friend request not found.");
        }
        Friendship friendship = optionalFriendship.get();
        friendship.setAccepted(true);
        return friendshipRepository.save(friendship);
    }

    // Get all pending friend requests for a user
    public List<Friendship> getPendingRequests(String receiverEmail) {
        return friendshipRepository.findByReceiverEmailAndIsAcceptedFalse(receiverEmail);
    }

    // Get all friends for a user
    public List<Friendship> getFriends(String email) {
        List<Friendship> sentFriends = friendshipRepository.findBySenderEmailAndIsAcceptedTrue(email);
        List<Friendship> receivedFriends = friendshipRepository.findByReceiverEmailAndIsAcceptedTrue(email);
        sentFriends.addAll(receivedFriends); // Combine both lists
        return sentFriends;
    }
}
