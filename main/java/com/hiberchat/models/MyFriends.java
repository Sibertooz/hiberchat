package com.hiberchat.models;

import javax.persistence.*;

@Entity
@Table(name = "MY_FRIENDS")
public class MyFriends {
    @Id
    @SequenceGenerator(name = "friends_seq", sequenceName = "friends_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friends_seq")
    private Long id;

    @Column(name = "MY_ID")
    private Long myId;

    @Column(name = "FRIEND_ID")
    private Long friendId;

    public MyFriends() {}

    public MyFriends(Long myId, Long friendId) {
        this.myId = myId;
        this.friendId = friendId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

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
