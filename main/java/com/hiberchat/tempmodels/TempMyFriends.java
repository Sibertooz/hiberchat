package com.hiberchat.tempmodels;

public class TempMyFriends {
    private Long id;

    private Long myId;

    private Long friendId;

    public void setMyId(Long myId) {
        this.myId = myId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Long getMyId() {
        return myId;
    }

    public Long getFriendId() {
        return friendId;
    }
}
