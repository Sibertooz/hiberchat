package com.hiberchat.services;

import com.hiberchat.models.Message;

import java.util.List;

public interface MessageService {
    void addMessage(Message message);
    Message getMessageById(Long messageId);
    List<Message> getAllMessages(Long fromUserId, Long toUserId);
    void deleteHistory(Long fromUserId, Long toUserId);
}
