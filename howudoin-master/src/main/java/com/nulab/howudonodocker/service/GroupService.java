package com.nulab.howudonodocker.service;

import com.nulab.howudonodocker.SimpleJwt;
import com.nulab.howudonodocker.model.Group;
import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.repository.GroupRepository;
import com.nulab.howudonodocker.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.nulab.howudonodocker.SimpleJwt.validateToken;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MessageRepository messageRepository;



    // Create a new group
    public Group createGroup(Group group) {

        if (group.getMembers() == null || group.getMembers().isEmpty()) {
            throw new RuntimeException("Group must have at least one member.");
        }
        return groupRepository.save(group);
    }

    // Add a new member to an existing group
    public void addMember(String groupId, String newMemberEmail) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        if (group.getMembers().contains(newMemberEmail)) {
            throw new RuntimeException("Member already exists in the group.");
        }
        group.addMember(newMemberEmail);
        groupRepository.save(group);
    }

    // Send a message to all members of a group
    public void sendGroupMessage(String groupId, Message message) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        // Iterate over group members and send the message to each
        for (String member : group.getMembers()) {
            Message groupMessage = new Message();
            groupMessage.setSenderEmail(message.getSenderEmail());
            groupMessage.setReceiverEmail(member);
            groupMessage.setContent(message.getContent());
            groupMessage.setGroupId(groupId);
            messageRepository.save(groupMessage);
        }
    }

    // Retrieve the message history for a group
    public List<Message> getGroupMessages(String groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        return messageRepository.findByGroupId(groupId);
    }

    // Retrieve the list of members in a group
    public List<String> getGroupMembers(String groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        return group.getMembers();
    }

}
