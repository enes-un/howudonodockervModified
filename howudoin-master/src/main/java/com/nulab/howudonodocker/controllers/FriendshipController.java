package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.SimpleJwt;
import com.nulab.howudonodocker.model.Friendship;
import com.nulab.howudonodocker.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.nulab.howudonodocker.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    // Send a friend request
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Friendship>> sendFriendRequest(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Friendship request) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token, request.getSenderEmail())) {
            Friendship friendship = friendshipService.sendFriendRequest(request.getSenderEmail(), request.getReceiverEmail());
            return ResponseEntity.ok(new ApiResponse<>(200, "Friend request sent successfully", friendship));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Invalid token", null));
        }
    }

    // Accept a friend request
    @PostMapping("/accept")
    public ResponseEntity<ApiResponse<Friendship>> acceptFriendRequest(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Friendship request) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token, request.getReceiverEmail())) {
            Friendship friendship = friendshipService.acceptFriendRequest(request.getSenderEmail(), request.getReceiverEmail());
            return ResponseEntity.ok(new ApiResponse<>(200, "Friend request accepted", friendship));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Invalid token", null));
        }
    }
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Friendship>>> getPendingFriendships(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String receiverEmail
    ){
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token, receiverEmail)) {
            List<Friendship> requests = friendshipService.getPendingRequests(receiverEmail);
            return ResponseEntity.ok(new ApiResponse<>(200, "Rquests retrieved successfully", requests));

        }
        else
        {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Invalid token", null));
        }
    }
    // Get friends for a user
    @GetMapping
    public ResponseEntity<ApiResponse<List<Friendship>>> getFriends(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String email) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            List<Friendship> friends = friendshipService.getFriends(email);
            return ResponseEntity.ok(new ApiResponse<>(200, "Friends retrieved successfully", friends));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Invalid token", null));
        }
    }

}
