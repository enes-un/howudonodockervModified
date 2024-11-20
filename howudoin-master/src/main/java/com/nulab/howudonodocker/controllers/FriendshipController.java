package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.model.Friendship;
import com.nulab.howudonodocker.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    // Send a friend request
    @PostMapping("/send")
    public Friendship sendFriendRequest(@RequestBody Friendship request) {
        return friendshipService.sendFriendRequest(request.getSenderEmail(), request.getReceiverEmail());
    }

    // Accept a friend request
    @PostMapping("/accept")
    public Friendship acceptFriendRequest(@RequestBody Friendship request) {
        return friendshipService.acceptFriendRequest(request.getSenderEmail(), request.getReceiverEmail());
    }

    // Get friends for a user
    @GetMapping("/friends")
    public List<Friendship> getFriends(@RequestParam String email) {
        return friendshipService.getFriends(email);
    }
}
