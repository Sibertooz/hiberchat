package com.hiberchat.tempmodels;

import java.time.LocalDateTime;

public class TempMessage {
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private LocalDateTime sendingDate;

    private String messageText;

    public Long getMessageId() {
        return id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public LocalDateTime getSendingDate() {
        return sendingDate;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageId(Long id) {
        this.id = id;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public void setSendingDate(LocalDateTime sendingDate) {
        this.sendingDate = sendingDate;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
