package com.hiberchat;

import com.hiberchat.models.Message;
import com.hiberchat.models.User;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class AssertUtils {
    private static final Logger logger = Logger.getLogger(AssertUtils.class.getName());

    public static void assertUsers(User expectedUser, User actualUser) {
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getNickname(), actualUser.getNickname());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    public static void assertUsersList(List<User> expectedList, List<User> actualList) {
        expectedList.sort(Comparator.comparing(User::getId));
        actualList.sort(Comparator.comparing(User::getId));

        if (expectedList.size() == actualList.size()) {
            for (int i = 0; i < expectedList.size(); i++) assertUsers(expectedList.get(i), actualList.get(i));
        } else logger.warning("Lists sizes aren't equals");
    }

    public static void assertMessages(Message expectedMessage, Message actualMessage) {
        assertEquals(expectedMessage.getMessageId(), actualMessage.getMessageId());
        assertEquals(expectedMessage.getMessageText(), actualMessage.getMessageText());
        assertEquals(expectedMessage.getToUserId(), actualMessage.getToUserId());
        assertEquals(expectedMessage.getSendingDate(), actualMessage.getSendingDate());
        assertEquals(expectedMessage.getFromUserId(), actualMessage.getFromUserId());
    }

    public static void assertMessagesList(List<Message> expectedList, List<Message> actualList) {
        expectedList.sort(Comparator.comparing(Message::getMessageId));
        actualList.sort(Comparator.comparing(Message::getMessageId));

        if (expectedList.size() == actualList.size()) {
            for (int i = 0; i < expectedList.size(); i++) assertMessages(expectedList.get(i), actualList.get(i));
        } else logger.warning("Lists sizes aren't equals");
    }
}
