package com.hiberchat.exceptions;

import com.hiberchat.utils.ExceptionMessages;

public class NullObjectException extends RuntimeException {
    public NullObjectException() {
        super(ExceptionMessages.NULL_OBJECT);
    }
}
