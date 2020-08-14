package com.hiberchat.exceptions;

import com.hiberchat.models.Message;

public class MessageException extends RuntimeException {
    private Message message;

    public MessageException(String exceptionMsg) {
        super(exceptionMsg);
    }

    public MessageException(String exceptionMsg, Message message) {
        this(exceptionMsg);
        this.message = message;
    }

    public Message getMessageAccount() {
        return message;
    }
}
