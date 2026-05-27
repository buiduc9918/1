package com.example.myapplication.models.sharesimple;

import java.util.List;

public class ChatRoomModel {
    private String chatRoomId;
    private List<String> userIds;
    private long lastMessageTimestamp;
    private String lastMessage;
    private String lastMessageSenderId;

    public ChatRoomModel() {
        // Required empty constructor for Firebase
    }

    public ChatRoomModel(String chatRoomId, List<String> userIds, long lastMessageTimestamp, String lastMessage) {
        this.chatRoomId = chatRoomId;
        this.userIds = userIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.lastMessage = lastMessage;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public long getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(long lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(String lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
