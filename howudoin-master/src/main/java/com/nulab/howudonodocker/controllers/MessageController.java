package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.SimpleJwt;
import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nulab.howudonodocker.model.ApiResponse;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Endpoint to send a message
    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> sendMessage(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Message message) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            try {
                messageService.sendMessage(message);  // Try sending the message
                return ResponseEntity.ok(new ApiResponse<>(200, "Message sent successfully", null));  // Success response
            } catch (RuntimeException e) {
                return ResponseEntity.status(400).body(new ApiResponse<>(400, e.getMessage(), null));  // Error response if not friends
            }
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized attempt", null));
        }
    }

    // Endpoint to get conversation between two users
    @GetMapping()
    public ResponseEntity<ApiResponse<List<Message>>> getConversation(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String user1Email,
            @RequestParam String user2Email) {
        String token = authorizationHeader.replace("Bearer ", "");

        if (SimpleJwt.validateToken(token, user1Email) || SimpleJwt.validateToken(token, user2Email)) {
            List<Message> conversation = messageService.getConversation(user1Email, user2Email);
            return ResponseEntity.ok(new ApiResponse<>(200, "Conversation retrieved successfully", conversation));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized attempt", null));
        }
    }
}
