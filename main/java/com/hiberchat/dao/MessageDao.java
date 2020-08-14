package com.hiberchat.dao;

import com.hiberchat.models.Message;

import java.util.List;

public interface MessageDao {
    void addMessage(Message message);
    Message getMessageById(Long messageId);
    List<Message> getAllMessages(Long fromUserId, Long toUserId);
    List<Message> list();
    void deleteHistory(Long fromUserId, Long toUserId);

    String GET_ALL_MESSAGES_BY_USERS_IDS = "from Message where fromUserId = :fromUserId and toUserId = :toUserId";
    String DELETE_HISTORY = "delete Message where fromUserId = :fromUserId and toUserId = :toUserId";
}
