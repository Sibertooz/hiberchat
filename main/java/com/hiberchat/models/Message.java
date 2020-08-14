package com.hiberchat.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGES")
public class Message {
    @Id
    @SequenceGenerator(name = "message_seq", sequenceName = "message_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    private Long id;

    @Column(name = "FROM_USER_ID")
    private Long fromUserId;

    @Column(name = "TO_USER_ID")
    private Long toUserId;

    @Column(name = "SENDING_DATE")
    private LocalDateTime sendingDate;

    @Column(name = "MESSAGE_TEXT")
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

    public class Builder {
        public Builder setMessageId(Long id) {
            Message.this.id = id;
            return this;
        }

        public Builder setFromUserId(Long fromUserId) {
            Message.this.fromUserId = fromUserId;
            return this;
        }

        public Builder setToUserId(Long toUserId) {
            Message.this.toUserId = toUserId;
            return this;
        }

        public Builder setSendingDate(LocalDateTime sendingDate) {
            Message.this.sendingDate = sendingDate;
            return this;
        }

        public Builder setMessageText(String messageText) {
            Message.this.messageText = messageText;
            return this;
        }

        public Message build() {
            return Message.this;
        }
    }

    public Message() {}
}
