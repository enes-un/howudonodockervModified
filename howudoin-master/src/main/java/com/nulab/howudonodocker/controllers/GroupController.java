package com.nulab.howudonodocker.controllers;

import com.nulab.howudonodocker.model.Group;
import com.nulab.howudonodocker.model.Message;
import com.nulab.howudonodocker.service.GroupService;
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
    public String createGroup(@RequestBody Group group) {
        groupService.createGroup(group);
        return "Group created successfully.";
    }

    // 2. Add a new member to an existing group
    @PostMapping("/{groupId}/add-member")
    public String addMember(@PathVariable String groupId, @RequestParam String newMemberEmail) {
        groupService.addMember(groupId, newMemberEmail);
        return "Member added to the group.";
    }

    // 3. Send a message to all members of a group
    @PostMapping("/{groupId}/send")
    public String sendGroupMessage(@PathVariable String groupId, @RequestBody Message message) {
        groupService.sendGroupMessage(groupId, message);
        return "Message sent to group members.";
    }

    // 4. Retrieve the message history for a group
    @GetMapping("/{groupId}/messages")
    public List<Message> getGroupMessages(@PathVariable String groupId) {
        return groupService.getGroupMessages(groupId);
    }

    // 5. Retrieve the list of members in a group
    @GetMapping("/{groupId}/members")
    public List<String> getGroupMembers(@PathVariable String groupId) {
        return groupService.getGroupMembers(groupId);
    }
}
