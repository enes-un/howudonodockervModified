package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Endpoint to send a message
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message message) {
        try {
            messageService.sendMessage(message);  // Try sending the message
            return new ResponseEntity<>("Message sent successfully.", HttpStatus.OK);  // Success response
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Error response if not friends
        }
    }

    // Endpoint to get all messages
    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();  // Return all messages
    }

    // Endpoint to get conversation between two users
    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam String user1Email, @RequestParam String user2Email) {
        return messageService.getConversation(user1Email, user2Email);  // Return conversation between two users
    }
}
