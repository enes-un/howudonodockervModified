package com.nulab.howudonodocker.repository;

import com.nulab.howudonodocker.model.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends MongoRepository<Friendship, String> {
    List<Friendship> findByReceiverEmailAndIsAcceptedFalse(String receiverEmail);
    List<Friendship> findBySenderEmailAndIsAcceptedTrue(String senderEmail);
    List<Friendship> findByReceiverEmailAndIsAcceptedTrue(String receiverEmail);
    Optional<Friendship> findBySenderEmailAndReceiverEmail(String senderEmail, String receiverEmail);
}