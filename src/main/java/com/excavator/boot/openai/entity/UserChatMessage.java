package com.excavator.boot.openai.entity;

import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public class UserChatMessage {
    private String msgId;
    private List<ChatMessage> chatMessageList;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public List<ChatMessage> getChatMessageList() {
        return chatMessageList;
    }

    public void setChatMessageList(List<ChatMessage> chatMessageList) {
        this.chatMessageList = chatMessageList;
    }
}
