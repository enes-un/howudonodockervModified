package com.nulab.howudonodocker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "groups")
public class Group {
    @Id
    private String id;
    private String name;
    private List<String> members = new ArrayList<>();

    // Add a new member
    public void addMember(String member) {
        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }

    // Remove a member
    public void removeMember(String member) {
        this.members.remove(member);
    }

    public List<String> getMembers() {
        return members;
    }
}
