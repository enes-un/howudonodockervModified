package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.SimpleJwt;
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
    public Friendship sendFriendRequest(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestBody Friendship request) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token, request.getSenderEmail())) {
            return friendshipService.sendFriendRequest(request.getSenderEmail(), request.getReceiverEmail());
        }
        else
            return null;
    }

    // Accept a friend request
    @PostMapping("/accept")
    public Friendship acceptFriendRequest(@RequestHeader("Authorization") String authorizationHeader,
                                          @RequestBody Friendship request) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token, request.getReceiverEmail()))
        {
            return friendshipService.acceptFriendRequest(request.getSenderEmail(), request.getReceiverEmail());
        }
        else
            return null;

    }

    // Get friends for a user
    @GetMapping("/friends")
    public List<Friendship> getFriends(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String email) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token))
        {
            return friendshipService.getFriends(email);
        }
        else
            return null;
    }
}
