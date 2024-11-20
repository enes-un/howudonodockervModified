package com.nulab.howudonodocker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "friendships")
public class Friendship {

    @Id
    private String id;
    private String senderEmail;
    private String receiverEmail;
    private boolean isAccepted;

    public void setSenderEmail(String senderEmail) {this.senderEmail = senderEmail;}
    public void setReceiverEmail(String receiverEmail) {this.receiverEmail = receiverEmail;}
    public void setAccepted(boolean isAccepted) {this.isAccepted = isAccepted;}
    public String getSenderEmail() {return senderEmail;}
    public String getReceiverEmail() {return receiverEmail;}
    public boolean isAccepted() {return isAccepted;}
}

