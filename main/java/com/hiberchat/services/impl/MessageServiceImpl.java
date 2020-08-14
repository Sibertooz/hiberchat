package com.hiberchat.services.impl;

import com.hiberchat.dao.MessageDao;
import com.hiberchat.exceptions.MessageException;
import com.hiberchat.exceptions.NullObjectException;
import com.hiberchat.models.Message;
import com.hiberchat.services.MessageService;
import com.hiberchat.utils.ExceptionMessages;
import com.hiberchat.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public void addMessage(Message message) {
        try {
            ExceptionUtil.messageIsNotEmpty(message);
        } catch (NullObjectException | IllegalArgumentException ex) {
            throw new MessageException(ExceptionMessages.MESSAGE_EMPTY);
        }

        messageDao.addMessage(message);
    }

    @Override
    public Message getMessageById(Long messageId) {
        try {
            ExceptionUtil.isNotNull(messageId);
        } catch (NullObjectException ex) {
            throw new MessageException(ExceptionMessages.NULL_OBJECT);
        }
        return messageDao.getMessageById(messageId);
    }

    @Override
    public List<Message> getAllMessages(Long fromUserId, Long toUserId) {
        ExceptionUtil.isNotNull(fromUserId, toUserId);
        List<Message> allMessages = new ArrayList<>(messageDao.getAllMessages(fromUserId, toUserId));
        allMessages.addAll(messageDao.getAllMessages(toUserId, fromUserId));
        allMessages.sort(Comparator.comparing(Message::getSendingDate));
        return allMessages;
    }

    @Override
    public void deleteHistory(Long fromUserId, Long toUserId) {
        ExceptionUtil.isNotNull(fromUserId, toUserId);

        if (!this.getAllMessages(fromUserId, toUserId).isEmpty()) messageDao.deleteHistory(fromUserId, toUserId);
        if (!this.getAllMessages(toUserId, fromUserId).isEmpty()) messageDao.deleteHistory(toUserId, fromUserId);
    }
}
