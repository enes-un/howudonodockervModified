package com.nulab.howudonodocker.repository;

import com.nulab.howudonodocker.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    // Custom query to find messages exchanged between two participants
    @Query("{ $or: [ { 'senderEmail': ?0, 'receiverEmail': ?1 }, { 'senderEmail': ?1, 'receiverEmail': ?0 } ] }")
    List<Message> findByParticipants(String user1Email, String user2Email);

    List<Message> findByGroupId(String groupId);
}


