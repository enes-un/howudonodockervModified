package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.model.Group;
import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.service.GroupService;
import com.nulab.howudonodocker.SimpleJwt;  // Import your SimpleJwt class for validation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // 1. Create a new group
    @PostMapping("/create")
    public String createGroup(@RequestHeader("Authorization") String authorizationHeader,
                              @RequestBody Group group) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Validate the token and user credentials
        if (SimpleJwt.validateToken(token)) {
            groupService.createGroup(group);
            return "Group created successfully.";
        } else {
            return "Unauthorized: Invalid token or credentials.";
        }
    }

    // 2. Add a new member to an existing group
    @PostMapping("/{groupId}/add-member")
    public String addMember(@RequestHeader("Authorization") String authorizationHeader,
                            @PathVariable String groupId,
                            @RequestParam String newMemberEmail) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user credentials from the request body (assume they're included)
        // Assuming you have an email and password field in your request body

        // Validate the token and user credentials
        if (SimpleJwt.validateToken(token)) {
            groupService.addMember(groupId, newMemberEmail);
            return "Member added to the group.";
        } else {
            return "Unauthorized: Invalid token or credentials.";
        }
    }

    // 3. Send a message to all members of a group
    @PostMapping("/{groupId}/send")
    public String sendGroupMessage(@RequestHeader("Authorization") String authorizationHeader,
                                   @PathVariable String groupId,
                                   @RequestBody Message message) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Extract user credentials from the message body (assuming this is how it works)
        String email = message.getSenderEmail();  // Assuming Message has sender's email
        String password = "userPassword";  // Assume this is sent by the user too

        // Validate the token and user credentials
        if (SimpleJwt.validateToken(token)) {
            groupService.sendGroupMessage(groupId, message);
            return "Message sent to group members.";
        } else {
            return "Unauthorized: Invalid token or credentials.";
        }
    }

    // 4. Retrieve the message history for a group
    @GetMapping("/{groupId}/messages")
    public List<Message> getGroupMessages(@RequestHeader("Authorization") String authorizationHeader,
                                          @PathVariable String groupId) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // You could validate the token here before proceeding to get messages
        if (SimpleJwt.validateToken(token)) {  // Replace with real credentials
            return groupService.getGroupMessages(groupId);
        } else {
            return null;  // Or throw an exception for unauthorized access
        }
    }

    // 5. Retrieve the list of members in a group
    @GetMapping("/{groupId}/members")
    public List<String> getGroupMembers(@RequestHeader("Authorization") String authorizationHeader,
                                        @PathVariable String groupId) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Validate the token before retrieving group members
        if (SimpleJwt.validateToken(token)) {  // Replace with real credentials
            return groupService.getGroupMembers(groupId);
        } else {
            return null;  // Or throw an exception for unauthorized access
        }
    }
}
