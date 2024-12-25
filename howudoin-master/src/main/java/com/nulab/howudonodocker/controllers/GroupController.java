package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.model.Group;
import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.service.GroupService;
import com.nulab.howudonodocker.SimpleJwt;  // Import your SimpleJwt class for validation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nulab.howudonodocker.model.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // 1. Create a new group
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createGroup(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Group group) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            Group createdGroup = groupService.createGroup(group);
            return ResponseEntity.ok(new ApiResponse<>(200, "Group created successfully", createdGroup.getId()));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized: Invalid token or credentials", null));
        }
    }

    // 2. Add a new member to an existing group
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<ApiResponse<String>> addMember(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupId,
            @RequestParam String newMemberEmail) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            groupService.addMember(groupId, newMemberEmail);
            return ResponseEntity.ok(new ApiResponse<>(200, "Member added to the group", null));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized: Invalid token or credentials", null));
        }
    }

    // 3. Send a message to all members of a group
    @PostMapping("/{groupId}/send")
    public ResponseEntity<ApiResponse<String>> sendGroupMessage(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupId,
            @RequestBody Message message) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            groupService.sendGroupMessage(groupId, message);
            return ResponseEntity.ok(new ApiResponse<>(200, "Message sent to group members", null));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized: Invalid token or credentials", null));
        }
    }

    // 4. Retrieve the message history for a group
    @GetMapping("/{groupId}/messages")
    public ResponseEntity<ApiResponse<List<Message>>> getGroupMessages(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupId) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            List<Message> messages = groupService.getGroupMessages(groupId);
            return ResponseEntity.ok(new ApiResponse<>(200, "Message history retrieved successfully", messages));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized: Invalid token or credentials", null));
        }
    }

    // 5. Retrieve the list of members in a group
    @GetMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<List<String>>> getGroupMembers(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String groupId) {
        String token = authorizationHeader.replace("Bearer ", "");
        if (SimpleJwt.validateToken(token)) {
            List<String> members = groupService.getGroupMembers(groupId);
            return ResponseEntity.ok(new ApiResponse<>(200, "Group members retrieved successfully", members));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "Unauthorized: Invalid token or credentials", null));
        }
    }

}
