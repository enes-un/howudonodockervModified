package com.nulab.howudonodocker.service;

import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Message message) {
        // Save the message to the database
        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        // Retrieve all messages
        return messageRepository.findAll();
    }

    public List<Message> getConversation(String user1Email, String user2Email) {
        // Retrieve messages between two users
        return messageRepository.findByParticipants(user1Email, user2Email);
    }
}
