package com.hiberchat.exceptions;

import com.hiberchat.models.User;

public class UserException extends RuntimeException {
    private User user;
    private int errorCode;

    public UserException(String exceptionMsg) {
        super(exceptionMsg);
    }

    public UserException(String exceptionMsg, int errorCode) {
        this(exceptionMsg);
        this.errorCode = errorCode;
    }

    public UserException(String exceptionMsg, User user) {
        this(exceptionMsg);
        this.user = user;
    }

    public User getUserAccount() {
        return user;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
