package com.hiberchat.models.enums;

import java.math.BigInteger;

public enum UserStatus {
    NO (new BigInteger("1")),
    YES (new BigInteger("2"));

    private BigInteger statusId;

    UserStatus(BigInteger statusId) {
        this.statusId = statusId;
    }

    public BigInteger getStatusId() {
        return statusId;
    }

    public static UserStatus getStatusByKey(BigInteger key) {
        for (UserStatus c : values()) if (c.getStatusId().equals(key)) return c;
        throw new IllegalArgumentException();
    }
}

