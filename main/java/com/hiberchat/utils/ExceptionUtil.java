package com.hiberchat.utils;

import com.hiberchat.exceptions.NullObjectException;
import com.hiberchat.models.Message;
import com.hiberchat.models.User;

public final class ExceptionUtil {
    public static void isNotNull(final Object... objects) {
        if (objects == null) throw new NullObjectException();
        for (final Object obj : objects) if (obj == null) throw new NullObjectException();
    }

    public static void userIsNotEmpty(User user) {
        isNotNull(user);
        isNotNull(user.getNickname(), user.getEmail(), user.getRegistrationDate());
        isNotEmptyString(user.getNickname(), user.getEmail());
    }

    public static void messageIsNotEmpty(Message message) {
        isNotNull(message);
        isNotNull(message.getFromUserId(), message.getToUserId(), message.getSendingDate(), message.getMessageText());
        isNotEmptyString(message.getMessageText());
    }

    public static void isNotEmptyString(final String... strings) {
        for (final String str : strings) if (str.trim().length() == 0) throw new IllegalArgumentException(ExceptionMessages.EMPTY_FIELD);
    }
}
