package com.hiberchat.services;

import com.hiberchat.models.MyFriends;
import com.hiberchat.models.User;

import java.util.List;

public interface MyFriendsService {
    void addMyFriend(MyFriends myFriend);
    void deleteMyFriend(Long myId, Long friendId);
    List<User> getAllMyFriends(Long myId);
}
